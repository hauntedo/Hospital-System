package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadRequestException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.OccupiedDataException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.UnavailableAppointment;
import ru.itis.hauntedo.simbirtest.exception.badrequest.UnavailableTimeTableException;
import ru.itis.hauntedo.simbirtest.exception.forbidden.CustomForbiddenException;
import ru.itis.hauntedo.simbirtest.exception.notfound.AppointmentNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.DoctorNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.MedicalServiceNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.UserNotFoundException;
import ru.itis.hauntedo.simbirtest.model.Appointment;
import ru.itis.hauntedo.simbirtest.model.Doctor;
import ru.itis.hauntedo.simbirtest.model.MedicalService;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.repository.AppointmentRepository;
import ru.itis.hauntedo.simbirtest.repository.DoctorRepository;
import ru.itis.hauntedo.simbirtest.repository.MedicalServiceRepository;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;
import ru.itis.hauntedo.simbirtest.service.AppointmentService;
import ru.itis.hauntedo.simbirtest.service.EmailService;
import ru.itis.hauntedo.simbirtest.utils.enums.AppointmentState;
import ru.itis.hauntedo.simbirtest.utils.mapper.AppointmentMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static ru.itis.hauntedo.simbirtest.specification.appointment.AppointmentSpecification.*;
import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.EMAIL_NOTIFY;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public AppointmentResponse makeAppointment(AppointmentRequest appointmentRequest, UserDetails userDetails) {
        log.info("Find medical service by Id: {}", appointmentRequest.getMedicalServiceId());
        MedicalService medicalService = medicalServiceRepository.findById(appointmentRequest.getMedicalServiceId())
                .orElseThrow(MedicalServiceNotFoundException::new);
        hasAvailableDoctorTimeTable(appointmentRequest.getDoctorId(), appointmentRequest.getStart());
        log.info("Find doctor by Id: {}", appointmentRequest.getDoctorId());
        Doctor doctor = checkDoctor(appointmentRequest, userDetails);
        Appointment newAppointment = appointmentMapper.toEntity(appointmentRequest);
        newAppointment.setAppointmentState(AppointmentState.OPENED);
        newAppointment.setMedicalService(medicalService);
        newAppointment.setDoctor(doctor);
        newAppointment.setStart(appointmentRequest.getStart().toLocalTime());
        newAppointment.setDate(appointmentRequest.getStart().toLocalDate());
        newAppointment.setPatient(userRepository.getUserByEmail(userDetails.getUsername()));
        newAppointment.setEnd(appointmentRequest.getStart().toLocalTime().plusSeconds(3600));
        //System.out.println(newAppointment.getDate() + " " + newAppointment.getStart() + " " + newAppointment.getEnd());
        log.info("Save appointment");
        Appointment appointment = appointmentRepository.save(newAppointment);
        log.info("Send notify message");
        sendNotifyEmails(appointment);
        return appointmentMapper.toResponse(appointment);
    }

    private Doctor checkDoctor(AppointmentRequest appointmentRequest, UserDetails userDetails) {
        Doctor doctor = doctorRepository
                .findById(appointmentRequest.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);
        if (doctor.getEmail().equals(userDetails.getUsername())) {
            log.error("Appointment yourself by doctor: {}", doctor.getEmail());
            throw new UnavailableAppointment("Doctor can't make appointments for himself");
        }
        return doctor;
    }

    private void sendNotifyEmails(Appointment appointment) {
        new Thread(() -> {
            Map<String, String> emailData = new HashMap<>();

            emailData.put("time", appointment.getDate().atTime(appointment.getStart()).toString());
            emailData.put("user_email", appointment.getDoctor().getEmail());
            emailService.send(emailData, "Notify about appointment", EMAIL_NOTIFY);
        }).start();
        new Thread(() -> {
            Map<String, String> emailData = new HashMap<>();
            emailData.put("time", appointment.getDate().atTime(appointment.getStart()).toString());
            emailData.put("user_email", appointment.getPatient().getEmail());
            emailService.send(emailData, "Notify about appointment", EMAIL_NOTIFY);
        }).start();
    }

    @Override
    public void cancelAppointment(UUID appointmentId, UserDetails userDetails) {
        Appointment appointment = checkUserId(appointmentId, userDetails);
        if (appointment.getAppointmentState().equals(AppointmentState.OPENED)) {
            throw new UnavailableAppointment("It is not possible to delete a appointment, if it has already been cancelled, completed");
        }
        appointmentRepository.delete(appointment);
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(UUID appointmentId, UserDetails userDetails, UpdateAppointmentRequest appointmentRequest) {
        Appointment appointment = checkUserId(appointmentId, userDetails);

        hasAvailableDoctorTimeTable(appointmentRequest.getDoctorId(), appointmentRequest.getStart());
        if (!medicalServiceRepository.existsByDoctorId(appointmentRequest.getDoctorId(), appointment.getMedicalService().getId())) {
            log.error("Doctor unavailable {}", appointmentRequest.getDoctorId());
            throw new BadRequestException("Unavailable doctor " +
                    appointmentRequest.getDoctorId() + " for medical service " + appointment.getMedicalService().getId());
        }
        appointmentMapper.updateEntity(appointment, appointmentRequest);
        appointment.setStart(appointmentRequest.getStart().toLocalTime());
        appointment.setDate(appointmentRequest.getStart().toLocalDate());
        appointment.setEnd(appointmentRequest.getStart().toLocalTime().plusSeconds(3600));
        log.info("Update appointment by id: {}", appointmentId);
        appointmentRepository.save(appointment);
        sendNotifyEmails(appointment);
        return appointmentMapper.toResponse(appointment);
    }

    //Извиняюсь за хардкор :)

    private void hasAvailableDoctorTimeTable(UUID doctorId, LocalDateTime appointmentTime) {
        List<Appointment> appointments = appointmentRepository.findAllByDateAndDoctor_Id(appointmentTime.toLocalDate(),doctorId);
        log.info("Appointments size: {}", appointments.size());
        isAvailableTime(appointmentTime);
        LocalTime lt = appointmentTime.toLocalTime();
        //время приема 1 час
        for (Appointment appointment : appointments) {
            log.info("local time {} start {} end {}", lt, appointment.getStart(), appointment.getEnd());
//            if (lt.isAfter(appointment.getStart()) && lt.isBefore(appointment.getEnd())) {
//                log.error("Occupied time of appointment: {}", appointmentTime);
//                throw new OccupiedDataException("Occupied time of appointment");
//            }
            if (Math.abs(Duration.between(lt, appointment.getStart()).getSeconds()) < 3600) {
                log.error("Occupied time of appointment: {}", appointmentTime);
                throw new OccupiedDataException("Occupied time of appointment");
            }
        }
    }

    private static void isAvailableTime(LocalDateTime appointmentTime) {
        //прошлое время
        if (appointmentTime.isBefore(LocalDateTime.now())) {
            log.error("Past time");
            throw new UnavailableTimeTableException("Unavailable time " + appointmentTime + ". Past time");
        }
        //максимум можно занять на две недели вперед
        if (appointmentTime.isAfter(LocalDateTime.now().plusWeeks(2))) {
            log.error("Future time");
            throw new UnavailableTimeTableException("Unavailable time " + appointmentTime + ". Max time for appointment 2 weeks.");
        }
        //время работы с 8 по 17
        if (!(appointmentTime.getHour() >= 8 && appointmentTime.getHour() <= 16)) {
            log.error("Unavailable time for doctor");
            throw new UnavailableTimeTableException("Unavailable time for doctor " + appointmentTime + ". No working time");
        }
    }

    private Appointment checkUserId(UUID appointmentId, UserDetails userDetails) {
        log.info("Find appointment by Id: {}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);
        if (!userDetails.getUsername().equals(appointment.getPatient().getEmail())) {
            throw new CustomForbiddenException("Forbidden");
        }
        return appointment;
    }

    //создал спецификации, но не успел полностью реализовать
    @Override
    public PageResponse<AppointmentResponse> getAvailableAppointmentByUser(UUID userId, int size, int page, LocalTime start, String op, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Specification<Appointment> spec = Specification.where(byPatient(user).and(byDateEqual(date).and(byStartEqual(start))));
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Appointment> result = appointmentRepository.findAll(spec, pageRequest);
        return PageResponse.<AppointmentResponse>builder()
                .totalElements(result.getTotalElements())
                .content(appointmentMapper.toList(result.getContent()))
                .totalPages(result.getTotalPages())
                .build();
    }

    //создал спецификации, но не успел полностью реализовать
    @Override
    public PageResponse<AppointmentResponse> getAvailableAppointmentByDoctor(UUID doctorId, int size, int page, LocalTime start, String op, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(UserNotFoundException::new);
        Specification<Appointment> spec = Specification.where(byDoctor(doctor).and(byDateEqual(date).and(byStartEqual(start))));
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Appointment> result = appointmentRepository.findAll(spec, pageRequest);
        return PageResponse.<AppointmentResponse>builder()
                .totalElements(result.getTotalElements())
                .content(appointmentMapper.toList(result.getContent()))
                .totalPages(result.getTotalPages())
                .build();
    }
}


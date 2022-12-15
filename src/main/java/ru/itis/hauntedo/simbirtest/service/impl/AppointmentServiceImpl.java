package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.OccupiedDataException;
import ru.itis.hauntedo.simbirtest.exception.forbidden.CustomForbiddenException;
import ru.itis.hauntedo.simbirtest.exception.notfound.AppointmentNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.DoctorNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.MedicalServiceNotFoundException;
import ru.itis.hauntedo.simbirtest.model.Appointment;
import ru.itis.hauntedo.simbirtest.model.MedicalService;
import ru.itis.hauntedo.simbirtest.repository.AppointmentRepository;
import ru.itis.hauntedo.simbirtest.repository.DoctorRepository;
import ru.itis.hauntedo.simbirtest.repository.MedicalServiceRepository;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;
import ru.itis.hauntedo.simbirtest.service.AppointmentService;
import ru.itis.hauntedo.simbirtest.utils.enums.AppointmentState;
import ru.itis.hauntedo.simbirtest.utils.mapper.AppointmentMapper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    private final MedicalServiceRepository medicalServiceRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public AppointmentResponse makeAppointment(AppointmentRequest appointmentRequest, UserDetails userDetails) {
        log.info("Find medical service by Id: {}", appointmentRequest.getMedicalServiceId());
        MedicalService medicalService = medicalServiceRepository.findById(appointmentRequest.getMedicalServiceId())
                .orElseThrow(MedicalServiceNotFoundException::new);
        List<Appointment> appointments = appointmentRepository.findAllByDoctor_Id(appointmentRequest.getDoctorId());
        for (Appointment appointment : appointments) {
            if (Duration.between(appointment.getDate(), appointmentRequest.getDate())
                    .get(ChronoUnit.SECONDS) < 3600
            &&
            appointment.getAppointmentState().equals(AppointmentState.OPENED)) {
                log.error("Occupied time of appointment: {}", appointmentRequest.getDate());
                throw new OccupiedDataException("Occupied time of appointment");
            }
        }
        Appointment newAppointment = appointmentMapper.toEntity(appointmentRequest);
        newAppointment.setAppointmentState(AppointmentState.OPENED);
        newAppointment.setMedicalService(medicalService);
        newAppointment.setDoctor(
                doctorRepository
                        .findById(appointmentRequest.getDoctorId())
                        .orElseThrow(DoctorNotFoundException::new));
        newAppointment.setPatient(userRepository.getUserByEmail(userDetails.getUsername()));
        log.info("Save appointment");
        return appointmentMapper.toResponse(appointmentRepository.save(newAppointment));
    }

    @Override
    public void cancelAppointment(UUID appointmentId, UserDetails userDetails) {
        log.info("Find appointment by Id: {}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);
        if (!userDetails.getUsername().equals(appointment.getPatient().getEmail())) {
            throw new CustomForbiddenException("Forbidden");
        }
        appointmentRepository.deleteById(appointmentId);
    }
}

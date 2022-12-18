package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentDetailRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentDetailResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadFileException;
import ru.itis.hauntedo.simbirtest.exception.notfound.AppointmentDetailNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.AppointmentNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.FileNotFoundException;
import ru.itis.hauntedo.simbirtest.model.Appointment;
import ru.itis.hauntedo.simbirtest.model.AppointmentDetail;
import ru.itis.hauntedo.simbirtest.model.AppointmentDetailFile;
import ru.itis.hauntedo.simbirtest.model.FileEntity;
import ru.itis.hauntedo.simbirtest.repository.AppointmentDetailFileRepository;
import ru.itis.hauntedo.simbirtest.repository.AppointmentDetailRepository;
import ru.itis.hauntedo.simbirtest.repository.AppointmentRepository;
import ru.itis.hauntedo.simbirtest.repository.FileRepository;
import ru.itis.hauntedo.simbirtest.service.AppointmentDetailService;
import ru.itis.hauntedo.simbirtest.utils.enums.FileType;
import ru.itis.hauntedo.simbirtest.utils.mapper.AppointmentDetailMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentDetailServiceImpl implements AppointmentDetailService {

    private final AppointmentDetailRepository appointmentDetailRepository;
    private final AppointmentDetailMapper appointmentDetailMapper;
    private final AppointmentRepository appointmentRepository;
    private final FileRepository fileRepository;
    private final AppointmentDetailFileRepository appointmentDetailFileRepository;
    @Override
    public AppointmentDetailResponse addAppointmentDetails(AppointmentDetailRequest appointmentDetailRequest, UUID appointmentId) {
        log.info("Find appointment by id: {}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(AppointmentNotFoundException::new);
        AppointmentDetail newAppointmentDetail = appointmentDetailMapper.toEntity(appointmentDetailRequest);
        newAppointmentDetail.setAppointment(appointment);
        log.info("Save appointment details");
        return appointmentDetailMapper.toResponse(appointmentDetailRepository.save(newAppointmentDetail));
    }

    @Override
    public AppointmentDetailResponse updateAppointmentDetails(AppointmentDetailRequest appointmentDetailRequest, UUID appointmentId) {
        log.info("Find appointment details by appointment id: {}", appointmentId);
        AppointmentDetail appointmentDetail = appointmentDetailRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(AppointmentDetailNotFoundException::new);
        appointmentDetailMapper.update(appointmentDetail, appointmentDetailRequest);
        log.info("update appointment details");
        return appointmentDetailMapper.toResponse(appointmentDetailRepository.save(appointmentDetail));
    }

    @Override
    public UUID saveAppointmentDetailFiles(UUID fileId, UUID appointmentId) {
        log.info("Find file by id: {}", fileId);
        FileEntity file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
        log.info("Find appointment detail by appointment id: {}", appointmentId);
        AppointmentDetail appointmentDetail = appointmentDetailRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(AppointmentDetailNotFoundException::new);
        if (!file.getFileType().equals(FileType.SCAN)) {
            log.error("Undefined file format");
            throw new BadFileException("Undefined file format");
        }
        AppointmentDetailFile detailFile = AppointmentDetailFile.builder()
                .file(file)
                .appointmentDetail(appointmentDetail)
                .build();
        log.info("Save appointment detail file");
        return appointmentDetailFileRepository.save(detailFile).getId();
    }
}

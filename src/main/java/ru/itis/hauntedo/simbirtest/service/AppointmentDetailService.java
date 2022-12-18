package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.dto.request.AppointmentDetailRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentDetailResponse;
import ru.itis.hauntedo.simbirtest.model.FileEntity;

import java.util.UUID;

public interface AppointmentDetailService {
    AppointmentDetailResponse addAppointmentDetails(AppointmentDetailRequest appointmentDetailRequest, UUID appointmentId);

    AppointmentDetailResponse updateAppointmentDetails(AppointmentDetailRequest appointmentDetailRequest, UUID appointmentId);

    UUID saveAppointmentDetailFiles(UUID fileId, UUID appointmentId);
}

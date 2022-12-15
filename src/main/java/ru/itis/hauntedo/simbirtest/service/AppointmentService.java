package ru.itis.hauntedo.simbirtest.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;

import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse makeAppointment(AppointmentRequest appointmentRequest, UserDetails userDetails);

    void cancelAppointment(UUID appointmentId, UserDetails userDetails);

    AppointmentResponse updateAppointment(UUID appointmentId, UserDetails userDetails, UpdateAppointmentRequest appointmentRequest);
}

package ru.itis.hauntedo.simbirtest.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse makeAppointment(AppointmentRequest appointmentRequest, UserDetails userDetails);

    void cancelAppointment(UUID appointmentId, UserDetails userDetails);

    AppointmentResponse updateAppointment(UUID appointmentId, UserDetails userDetails, UpdateAppointmentRequest appointmentRequest);

    PageResponse<AppointmentResponse> getAvailableAppointmentByUser(UUID userId, int size, int page, LocalTime start, String op, LocalDate date);

    PageResponse<AppointmentResponse> getAvailableAppointmentByDoctor(UUID doctorId, int size, int page, LocalTime start, String op, LocalDate date);

}

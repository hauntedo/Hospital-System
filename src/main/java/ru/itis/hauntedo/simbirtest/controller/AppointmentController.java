package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.AppointmentApi;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.service.AppointmentService;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AppointmentController implements AppointmentApi {

    private final AppointmentService appointmentService;


    @Override
    public ResponseEntity<AppointmentResponse> makeAppointment(
            AppointmentRequest appointmentRequest, UserDetails userDetails) {
        return ResponseEntity.status(201).body(appointmentService.makeAppointment(appointmentRequest, userDetails));
    }

    @Override
    public ResponseEntity<SuccessResponse> cancelAppointment(UUID appointmentId, UserDetails userDetails) {
        appointmentService.cancelAppointment(appointmentId, userDetails);
        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("Appointment cancelled successfully")
                        .time(Instant.now())
                .build());
    }
}

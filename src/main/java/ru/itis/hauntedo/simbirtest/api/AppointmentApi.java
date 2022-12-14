package ru.itis.hauntedo.simbirtest.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.model.Appointment;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RequestMapping("/api/appointments")
public interface AppointmentApi {

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentResponse> makeAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest);

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> cancelAppointment(@PathVariable("id") UUID appointmentId);

}

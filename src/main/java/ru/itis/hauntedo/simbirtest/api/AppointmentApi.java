package ru.itis.hauntedo.simbirtest.api;

import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentDetailRequest;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentDetailResponse;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.model.Appointment;

import javax.validation.Valid;

import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RequestMapping("/api/appointments")
public interface AppointmentApi {

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentResponse> makeAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest,
                                                        @AuthenticationPrincipal UserDetails userDetails);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> cancelAppointment(@PathVariable("id") UUID appointmentId,
                                                      @AuthenticationPrincipal UserDetails userDetails);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable("id") UUID appointmentId,
                                                          @AuthenticationPrincipal UserDetails userDetails,
                                                          @RequestBody UpdateAppointmentRequest updateAppointmentRequest);

    @PreAuthorize("hasRole('DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(value = "/{id}/details", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDetailResponse> addAppointmentDetails(@RequestBody AppointmentDetailRequest appointmentDetailRequest,
                                                                    @PathVariable("id") UUID appointmentId);

    @PreAuthorize("hasRole('DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}/details", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDetailResponse> updateAppointmentDetails(@RequestBody AppointmentDetailRequest appointmentDetailRequest,
                                                                    @PathVariable("id") UUID appointmentId);


    @PreAuthorize("hasRole('DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(value = "/{id}/details/files", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> saveAppointmentDetailFile(@PathVariable("id") UUID appointmentId,@RequestParam("file") UUID fileID);

}

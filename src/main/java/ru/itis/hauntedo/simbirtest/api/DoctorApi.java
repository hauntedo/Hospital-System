package ru.itis.hauntedo.simbirtest.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.response.*;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.springframework.http.MediaType.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/api/doctors")
public interface DoctorApi {

    @Operation(summary = "Add doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor added",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = DoctorResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied doctor data or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationError.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorResponse> addDoctor(@RequestBody @Valid DoctorRequest doctorRequest);

    @Operation(summary = "Update doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = DoctorResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied doctor category or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationError.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorResponse> updateDoctor(@RequestBody @Valid UpdateDoctorRequest doctorRequest,
                                                @PathVariable("id") UUID doctorId);

    @Operation(summary = "Get doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor founded",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = DoctorResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorResponse> getDoctorById(@PathVariable("id") UUID doctorId);

    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of doctors",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema =
                                            @Schema(implementation = DoctorResponse.class))
                            )
                    })
    })
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponse<DoctorResponse>> getDoctors(@RequestParam(value = "page",required = false) int page,
                                                            @RequestParam(value = "size", required = false) int size,
                                                            @RequestParam(value = "service-code", required = false) String serviceCode,
                                                            @RequestParam(value = "category-code", required = false) String categoryCode);

    @Operation(summary = "Delete doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor deleted",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> deleteDoctorById(@PathVariable("id") UUID doctorId);

    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(value = "/{user-id}/appointments", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponse<AppointmentResponse>> getAppointments(
            @PathVariable("user-id") UUID userId,
            @RequestParam(name = "size", required = false) int size,
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start,
            @RequestParam(name = "op", required = false) String op,
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);


}

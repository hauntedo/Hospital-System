package ru.itis.hauntedo.simbirtest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.AddDoctorToServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateMedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.response.*;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import javax.validation.Valid;

import java.util.Set;
import java.util.UUID;

import static org.springframework.http.MediaType.*;

@RequestMapping("/api/medical-services")
public interface MedicalServiceApi {

    @Operation(summary = "Add medical service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service added",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied medical service or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationError.class))
                    })
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalServiceResponse> addMedicalService(@RequestBody @Valid MedicalServiceRequest medicalServiceRequest);

    @Operation(summary = "Get medical service by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service founded",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceCategoryResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Service not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalServiceResponse> getMedicalServiceById(@PathVariable("id") UUID medicalServiceId);

    @Operation(summary = "Get all medical services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of services",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema =
                                            @Schema(implementation = MedicalServiceCategoryResponse.class))
                            )
                    })
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponse<MedicalServiceResponse>> getMedicalServices(
            @RequestParam(value = "page",required = false) int page,
            @RequestParam(value = "size", required = false) int size
    );

    @Operation(summary = "Update medical service by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical service updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceCategoryResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied medical service or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationError.class))
                    })
    })
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalServiceResponse> updateMedicalServiceById(
            @RequestBody @Valid UpdateMedicalServiceRequest medicalServiceRequest,
            @PathVariable("id") UUID medicalServiceId);

    @Operation(summary = "Delete medical service by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical service deleted",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Medical service not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<SuccessResponse> deleteMedicalServiceById(@PathVariable("id") UUID medicalServiceId);

    @PostMapping(value = "/{id}/doctors", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Set<DoctorResponse>> addDoctorToService(@PathVariable("id") UUID medicalServiceId,
                                                           @RequestBody AddDoctorToServiceRequest serviceRequest);


}


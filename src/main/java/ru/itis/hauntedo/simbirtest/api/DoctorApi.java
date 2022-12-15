package ru.itis.hauntedo.simbirtest.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.response.*;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import javax.validation.Valid;
import java.util.UUID;

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
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> deleteDoctorById(@PathVariable("id") UUID doctorId);

}

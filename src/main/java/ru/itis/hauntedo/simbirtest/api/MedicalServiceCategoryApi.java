package ru.itis.hauntedo.simbirtest.api;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceCategoryRequest;
import ru.itis.hauntedo.simbirtest.dto.response.ExceptionResponse;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@RequestMapping("/api/medical-service-categories")
public interface MedicalServiceCategoryApi {


    @Operation(summary = "Get all medical service categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of categories",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema =
                                            @Schema(implementation = MedicalServiceCategoryResponse.class))
                            )
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponse<MedicalServiceCategoryResponse>> getMedicalServiceCategories(
            @RequestParam(value = "page",required = false) int page,
            @RequestParam(value = "size", required = false) int size);

    @Operation(summary = "Get medical service category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category founded",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceCategoryResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalServiceCategoryResponse> getMedicalServiceCategoryById(
            @PathVariable("id") UUID medicalServiceCategoryId);

    @Operation(summary = "Add medical service category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category added",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceCategoryResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied medical service category or validation error",
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
    ResponseEntity<MedicalServiceCategoryResponse> addMedicalServiceCategory(
            @RequestBody @Valid MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    @Operation(summary = "Update medical service category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical service category updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = MedicalServiceCategoryResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Occupied medical service category or validation error",
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
    ResponseEntity<MedicalServiceCategoryResponse> updateMedicalServiceCategoryById(
            @PathVariable("id") UUID medicalServiceCategoryId,
            @RequestBody @Valid MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    @Operation(summary = "Delete medical service category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical service category deleted",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Medical service category not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> deleteMedicalServiceCategoryById(@PathVariable("id") UUID medicalServiceCategoryId);
}

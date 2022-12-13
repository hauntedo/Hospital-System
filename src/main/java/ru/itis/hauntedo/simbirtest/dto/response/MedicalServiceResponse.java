package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "medical service", description = "info about medical service")
public class MedicalServiceResponse {

    @JsonProperty("id")
    @Schema(name = "medical service category id")
    private UUID id;

    @JsonProperty("title")
    @Schema(name = "medical service title", example = "проверка глаз")
    private String title;

    @JsonProperty("description")
    @Schema(name = "medical service description", example = "Что-то")
    private String description;

    @JsonProperty("cost")
    @Schema(name = "medical service cost", example = "500.0")
    private Double cost;

    @JsonProperty("medical_service_category")
    @Schema(name = "medical service category")
    private MedicalServiceCategoryResponse medicalServiceCategoryResponse;
}

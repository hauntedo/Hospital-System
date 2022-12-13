package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "medical service update request", description = "form for updating medical service")
public class UpdateMedicalServiceRequest {

    @JsonProperty("title")
    @Schema(name = "medical service category title")
    private String title;

    @JsonProperty("description")
    @Schema(name = "medical service category description")
    private String description;

    @JsonProperty("medical_service_cost")
    @Schema(name = "medical service cost")
    @PositiveOrZero
    private Double cost;

}

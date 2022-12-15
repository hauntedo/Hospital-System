package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "medical service request", description = "form for adding medical service")
public class MedicalServiceRequest {

    @JsonProperty("title")
    @NotBlank(message = "Shouldn't be blank")
    @Schema(name = "medical service category title")
    private String title;

    @JsonProperty("description")
    @Schema(name = "medical service category description")
    private String description;

    @JsonProperty("cost")
    @Schema(name = "medical service cost")
    @PositiveOrZero
    private Double cost;

    @JsonProperty("medical_service_category_id")
    @Schema(name = "medical service category id")
    private UUID medicalServiceCategoryId;

    @JsonProperty("code")
    @Schema(description = "medical service code")
    private String code;


}

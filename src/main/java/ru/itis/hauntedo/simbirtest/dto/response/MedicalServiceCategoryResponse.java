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
@Builder
@Data
@Schema(name = "medical service category", description = "info about medical service category")
public class MedicalServiceCategoryResponse {

    @JsonProperty("id")
    @Schema(name = "medical service category id")
    private UUID id;

    @JsonProperty("title")
    @Schema(name = "medical service category title", example = "лечебный")
    private String title;

    @JsonProperty("description")
    @Schema(name = "medical service category description", example = "Что-то")
    private String description;

    @JsonProperty("code")
    @Schema(description = "medical service category code")
    private String code;

}

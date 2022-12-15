package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "request for add medical service category")
public class MedicalServiceCategoryRequest {

    @JsonProperty("title")
    @NotBlank(message = "Shouldn't be blank")
    @Schema(description = "medical service category title")
    private String title;

    @JsonProperty("description")
    @Schema(description = "medical service category description")
    private String description;

    @JsonProperty("code")
    @NotBlank
    @Schema(description = "medical service category code")
    private String code;


}

package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "update doctor request", description = "update doctor")
public class UpdateDoctorRequest extends UpdateUserRequest {

    @JsonProperty("education")
    @Schema(name = "education")
    @NotBlank
    private String education;

    @JsonProperty("experience")
    @Schema(name = "experience")
    @PositiveOrZero
    private Integer experience;






}
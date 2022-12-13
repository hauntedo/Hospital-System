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
@Schema(name = "doctor request", description = "add doctor")
public class DoctorRequest extends CreateUserRequest {

    @JsonProperty("password")
    @Schema(name = "user password")
    @Pattern(regexp = PASSWORD_REGEX, message = "Must have at least one numeric character, " +
            "one lowercase character, one uppercase character, length should be between 8 and 20")
    @NotNull(message = "Should not be null")
    private String password;

    @JsonProperty("repeat_password")
    @Schema(name = "repeat password")
    @NotNull(message = "Should not be null")
    private String repeatPassword;

    @JsonProperty("education")
    @Schema(name = "education")
    @NotBlank
    private String education;

    @JsonProperty("experience")
    @Schema(name = "experience")
    @PositiveOrZero
    private Integer experience;


}
package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "user request", description = "registration form")
public class CreateUserRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "Should not be empty")
    @Schema(name = "first name", description = "Should not be empty")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Should not be empty")
    @Schema(name = "last name", description = "Should not be empty")
    private String lastName;

    @JsonProperty("middle_name")
    @Schema(name = "middle name")
    private String middleName;

    @JsonProperty("email")
    @Schema(name = "email", description = "Should not be empty")
    @Email(regexp = EMAIL_REGEX, message = "Invalid email")
    private String email;

    @JsonProperty("phone")
    @Pattern(regexp = PHONE_REGEX,
            message = "Incorrect number")
    @Schema(name = "phone", example = "80000000000, +79912112312")
    private String phone;

}

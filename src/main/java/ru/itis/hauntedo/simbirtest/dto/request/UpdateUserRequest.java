package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.EMAIL_REGEX;
import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.PHONE_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "form for update user")
public class UpdateUserRequest {

    @JsonProperty("first_name")
    @Schema(name = "first name")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(name = "last name")
    private String lastName;

    @JsonProperty("middle_name")
    @Schema(name = "middle name")
    private String middleName;

}

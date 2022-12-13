package ru.itis.hauntedo.simbirtest.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.PASSWORD_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "form for update user password")
public class UpdatePasswordRequest {

    @JsonProperty("old_password")
    @Schema(name = "old password")
    @NotNull(message = "Should not be null")
    private String oldPassword;

    @JsonProperty("new_password")
    @Schema(name = "new password")
    @Pattern(regexp = PASSWORD_REGEX, message = "Must have at least one numeric character, " +
            "one lowercase character, one uppercase character, length should be between 8 and 20")
    @NotNull(message = "Should not be null")
    private String newPassword;

    @JsonProperty("repeat_password")
    @Schema(name = "repeat password")
    @NotNull(message = "Should not be null")
    private String repeatPassword;

}

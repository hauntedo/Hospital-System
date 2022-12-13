package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "user login", description = "user login process")
public class UserRequest {

    @JsonProperty("email")
    @Schema(name = "email", description = "Should not be empty")
    private String email;

    @JsonProperty("password")
    @Schema(name = "user password")
    private String password;
}

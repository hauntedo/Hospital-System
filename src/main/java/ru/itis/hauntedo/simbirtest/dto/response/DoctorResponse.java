package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(name = "doctor", description = "info about doctor")
public class DoctorResponse {

    @JsonProperty("experience")
    @Schema(name = "experience", example = "3")
    private Integer experience;

    @JsonProperty("education")
    @Schema(name = "education", example = "something")
    private String education;

    @JsonProperty("id")
    @Schema(name = "user id")
    private UUID id;

    @JsonProperty("phone")
    @Schema(name = "phone", example = "800000000")
    private String phone;

    @JsonProperty("email")
    @Schema(name = "email", example = "rustem.karimov.2002@gmail.com")
    private String email;

    @JsonProperty("role")
    @Schema(name = "user role", example = "ADMIN")
    private String role;

    @JsonProperty("state")
    @Schema(name = "user state", example = "BANNED")
    private String state;

    @JsonProperty("first_name")
    @Schema(name = "first name", example = "Rustem")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(name = "last name", example = "Karimov")
    private String lastName;

    @JsonProperty("middle_name")
    @Schema(name = "middle name", example = "Rinatovich")
    private String middleName;

}

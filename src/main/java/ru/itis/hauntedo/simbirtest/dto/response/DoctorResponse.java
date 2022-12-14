package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @JsonProperty("user")
    @Schema(name = "user")
    private UserResponse userResponse;

    @JsonProperty("medical_services")
    @Schema(name = "medical_services")
    private Set<MedicalServiceResponse> medicalServices = new HashSet<>();

}

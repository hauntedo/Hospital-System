package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "appointment request")
public class AppointmentRequest {

    @JsonProperty("date")
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(name = "appointment date")
    private LocalDateTime date;

    @JsonProperty("medical_service_id")
    @NotBlank
    @Schema(name = "appointment medical service id")
    private UUID medicalServiceId;

    @JsonProperty("patient_comment")
    @Schema(name = "patient comment")
    private String patientComment;

    @JsonProperty("doctor_id")
    @NotBlank
    @Schema(name = "doctor id")
    private UUID doctorId;
}

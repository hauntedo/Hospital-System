package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "update appointment request", description = "form for updating appointment")
public class UpdateAppointmentRequest {

    @JsonProperty("doctor_id")
    @Schema(name = "doctor id")
    private UUID doctorId;

    @JsonProperty("start")
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss.zzz")
    @Schema(name = "appointment start time")
    private LocalDateTime start;
}

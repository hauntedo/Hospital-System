package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "update appointment request", description = "form for updating appointment")
public class UpdateAppointmentRequest {

    @JsonProperty("doctor_id")
    @Schema(name = "doctor id")
    private UUID doctorId;

    @JsonProperty("date")
    @Schema(name = "appointment date")
    private Instant date;
}

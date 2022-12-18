package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "appointment details", description = "info about appointment details")
public class AppointmentDetailResponse {

    @JsonProperty(value = "recommendation")
    @Schema(name = "recommendation")
    private String recommendation;

    @JsonProperty(value = "general_info")
    @Schema(name = "general_info")
    private String generalInfo;

    @JsonProperty(value = "anamnesis")
    @Schema(name = "anamnesis")
    private String anamnesis;
}

package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "appointment details request", description = "adding info about appointment details")
public class AppointmentDetailRequest {

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

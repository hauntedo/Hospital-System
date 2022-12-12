package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "success", description = "info about successful operation")
public class SuccessResponse {

    @JsonProperty("message")
    @Schema(name = "message")
    private String message;

    @JsonProperty("time")
    @Schema(name = "time")
    private Instant time;


}

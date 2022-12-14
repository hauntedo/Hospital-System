package ru.itis.hauntedo.simbirtest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "doctor to service request", description = "add doctor to service")
public class AddDoctorToServiceRequest {

    @JsonProperty("doctors")
    @Schema(name = "doctors")
    List<UUID> doctors = new ArrayList<>();
}

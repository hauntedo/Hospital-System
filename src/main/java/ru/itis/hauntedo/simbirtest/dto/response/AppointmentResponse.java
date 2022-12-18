package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "appointment response", description = "info about appointment")
public class AppointmentResponse {


    @JsonProperty("date")
    @Schema(name = "appointment date")
    private LocalDate date;


    @JsonProperty("start")
    @Schema(name = "appointment start")
    private LocalTime start;

    @JsonProperty("end")
    @Schema(name = "appointment end")
    private LocalTime end;


    @JsonProperty("patient_comment")
    @Schema(name = "patient comment")
    private String patientComment;

    @JsonProperty("medical_service")
    @Schema(name = "medical service")
    private MedicalServiceResponse medicalServiceResponse;

    @JsonProperty("doctor")
    @Schema(name = "doctor")
    private DoctorResponse doctorResponse;

    @JsonProperty("appointment_details")
    @Schema(name = "appointment details")
    private AppointmentDetailResponse appointmentDetailResponse;

}

package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.DoctorApi;
import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.response.DoctorResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.service.DoctorService;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class DoctorController implements DoctorApi {

    private final DoctorService doctorService;

    @Override
    public ResponseEntity<DoctorResponse> addDoctor(DoctorRequest doctorRequest) {
        return ResponseEntity.status(201).body(doctorService.addDoctor(doctorRequest));
    }

    @Override
    public ResponseEntity<DoctorResponse> updateDoctor(UpdateDoctorRequest doctorRequest, UUID doctorId) {
        return ResponseEntity.status(201).body(doctorService.updateDoctor(doctorId, doctorRequest));
    }

    @Override
    public ResponseEntity<DoctorResponse> getDoctorById(UUID doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorById(doctorId));
    }

    @Override
    public ResponseEntity<PageResponse<DoctorResponse>> getDoctors(int page, int size) {
        return ResponseEntity.ok(doctorService.getDoctors(page, size));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteDoctorById(UUID doctorId) {
        doctorService.deleteDoctorById(doctorId);
        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("Doctor deleted successfully")
                        .time(Instant.now())
                        .build());
    }
}

package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.MedicalServiceApi;
import ru.itis.hauntedo.simbirtest.dto.request.AddDoctorToServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateMedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.response.DoctorResponse;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.service.MedicalServiceService;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class MedicalServiceController implements MedicalServiceApi {

    private final MedicalServiceService medicalServiceService;

    @Override
    public ResponseEntity<MedicalServiceResponse> addMedicalService(MedicalServiceRequest medicalServiceRequest) {
        return ResponseEntity.status(201).body(medicalServiceService.addMedicalService(medicalServiceRequest));
    }

    @Override
    public ResponseEntity<MedicalServiceResponse> getMedicalServiceById(UUID medicalServiceId) {
        return ResponseEntity.ok(medicalServiceService.getMedicalServiceById(medicalServiceId));
    }

    @Override
    public ResponseEntity<PageResponse<MedicalServiceResponse>> getMedicalServicesByCategory(int page, int size, String categoryCode) {
        return ResponseEntity.ok(medicalServiceService.getMedicalServicesByCategory(page, size, categoryCode));
    }

//    @Override
//    public ResponseEntity<PageResponse<MedicalServiceResponse>> getMedicalServices(int page, int size) {
//        return ResponseEntity.ok(medicalServiceService.getMedicalServices(page, size));
//    }

    @Override
    public ResponseEntity<MedicalServiceResponse> updateMedicalServiceById(UpdateMedicalServiceRequest medicalServiceRequest, UUID medicalServiceId) {
        return ResponseEntity.status(201).body(medicalServiceService.updateMedicalServiceById(medicalServiceRequest, medicalServiceId));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteMedicalServiceById(UUID medicalServiceId) {
        medicalServiceService.deleteMedicalServiceById(medicalServiceId);
        return ResponseEntity.ok(SuccessResponse.builder()
                        .time(Instant.now())
                        .message("Medical service deleted successfully")
                .build());
    }

    @Override
    public ResponseEntity<Set<DoctorResponse>> addDoctorToService(UUID medicalServiceId, AddDoctorToServiceRequest serviceRequest) {
        return ResponseEntity.status(201).body(medicalServiceService.addDoctorToService(medicalServiceId, serviceRequest));
    }
}

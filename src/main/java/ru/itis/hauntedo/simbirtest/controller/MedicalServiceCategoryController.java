package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.MedicalServiceCategoryApi;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceCategoryRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.service.MedicalServiceCategoryService;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class MedicalServiceCategoryController implements MedicalServiceCategoryApi {

    private final MedicalServiceCategoryService medicalServiceCategoryService;

    @Override
    public ResponseEntity<PageResponse<MedicalServiceCategoryResponse>> getMedicalServiceCategories(int page, int size) {
        return ResponseEntity.ok(medicalServiceCategoryService.getMedicalServiceCategories(page, size));
    }

    @Override
    public ResponseEntity<MedicalServiceCategoryResponse> getMedicalServiceCategoryById(UUID medicalServiceCategoryId) {
        return ResponseEntity.ok(medicalServiceCategoryService.getMedicalServiceCategoryById(medicalServiceCategoryId));
    }

    @Override
    public ResponseEntity<MedicalServiceCategoryResponse> addMedicalServiceCategory(MedicalServiceCategoryRequest medicalServiceCategoryRequest) {
        return ResponseEntity.status(201)
                .body(medicalServiceCategoryService.addMedicalServiceCategory(medicalServiceCategoryRequest));
    }

    @Override
    public ResponseEntity<MedicalServiceCategoryResponse> updateMedicalServiceCategoryById(UUID medicalServiceCategoryId, MedicalServiceCategoryRequest medicalServiceCategoryRequest) {
        return ResponseEntity.status(201)
                .body(medicalServiceCategoryService.updateMedicalServiceCategoryById(
                        medicalServiceCategoryId, medicalServiceCategoryRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteMedicalServiceCategoryById(UUID medicalServiceCategoryId) {
        medicalServiceCategoryService.deleteMedicalServiceCategoryById(medicalServiceCategoryId);
        return ResponseEntity.ok(SuccessResponse.builder()
                .time(Instant.now())
                .message("Medical service category deleted successfully")
                .build());
    }
}

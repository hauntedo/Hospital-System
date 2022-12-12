package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceCategoryRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;

import java.util.UUID;

public interface MedicalServiceCategoryService {

    PageResponse<MedicalServiceCategoryResponse> getMedicalServiceCategories(int page, int size);

    MedicalServiceCategoryResponse getMedicalServiceCategoryById(UUID medicalServiceCategoryId);

    MedicalServiceCategoryResponse addMedicalServiceCategory(MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    MedicalServiceCategoryResponse updateMedicalServiceCategoryById(UUID medicalServiceCategoryId, MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    void deleteMedicalServiceCategoryById(UUID medicalServiceCategoryId);
}

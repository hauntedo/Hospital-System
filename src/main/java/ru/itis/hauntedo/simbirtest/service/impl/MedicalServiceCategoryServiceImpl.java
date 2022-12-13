package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceCategoryRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.OccupiedDataException;
import ru.itis.hauntedo.simbirtest.exception.notfound.MedicalServiceCategoryNotFoundException;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;
import ru.itis.hauntedo.simbirtest.repository.MedicalServiceCategoryRepository;
import ru.itis.hauntedo.simbirtest.service.MedicalServiceCategoryService;
import ru.itis.hauntedo.simbirtest.utils.mapper.MedicalServiceCategoryMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalServiceCategoryServiceImpl implements MedicalServiceCategoryService {

    private final MedicalServiceCategoryRepository medicalServiceCategoryRepository;
    private final MedicalServiceCategoryMapper medicalServiceCategoryMapper;

    @Override
    public PageResponse<MedicalServiceCategoryResponse> getMedicalServiceCategories(int page, int size) {
        log.info("Get list of medical service category");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MedicalServiceCategory> result = medicalServiceCategoryRepository
                .findAll(pageRequest);
        return PageResponse.<MedicalServiceCategoryResponse>builder()
                .content(medicalServiceCategoryMapper.toList(result.getContent()))
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public MedicalServiceCategoryResponse getMedicalServiceCategoryById(UUID medicalServiceCategoryId) {
        log.info("Find medical service category by ID: {}", medicalServiceCategoryId);
        return medicalServiceCategoryMapper
                .toMedicalServiceCategoryResponse(
                        medicalServiceCategoryRepository
                        .findById(medicalServiceCategoryId)
                        .orElseThrow(MedicalServiceCategoryNotFoundException::new));
    }

    @Override
    public MedicalServiceCategoryResponse addMedicalServiceCategory(
            MedicalServiceCategoryRequest medicalServiceCategoryRequest) {
        String categoryTitle = medicalServiceCategoryRequest.getTitle();
        log.info("Find medical service category by title: {}", categoryTitle);
        if (medicalServiceCategoryRepository.existsByTitle(categoryTitle)) {
            log.error("Medical service category already exists: {}", categoryTitle);
            throw new OccupiedDataException("Medical service category is occupied: " + categoryTitle);
        }
        MedicalServiceCategory newCategory = medicalServiceCategoryMapper
                .toMedicalServiceCategory(medicalServiceCategoryRequest);
        log.info("Save medical service category entity");
        return medicalServiceCategoryMapper.toMedicalServiceCategoryResponse(
                medicalServiceCategoryRepository.save(newCategory));
    }

    @Override
    public MedicalServiceCategoryResponse updateMedicalServiceCategoryById(
            UUID medicalServiceCategoryId,
            MedicalServiceCategoryRequest medicalServiceCategoryRequest) {
        log.info("Find medical service category by ID: {}", medicalServiceCategoryId);
        MedicalServiceCategory medicalServiceCategory = medicalServiceCategoryRepository
                .findById(medicalServiceCategoryId)
                .orElseThrow(MedicalServiceCategoryNotFoundException::new);
        medicalServiceCategoryMapper.updateMedicalServiceCategory(medicalServiceCategory,medicalServiceCategoryRequest);
        log.info("Update medical service category: {}", medicalServiceCategoryId);
        return medicalServiceCategoryMapper.toMedicalServiceCategoryResponse(
                medicalServiceCategoryRepository.save(medicalServiceCategory));
    }

    @Override
    public void deleteMedicalServiceCategoryById(UUID medicalServiceCategoryId) {
        log.info("Find medical service category by ID: {}", medicalServiceCategoryId);
        MedicalServiceCategory medicalServiceCategory = medicalServiceCategoryRepository
                .findById(medicalServiceCategoryId)
                .orElseThrow(MedicalServiceCategoryNotFoundException::new);
        log.info("Delete medical service category by ID: {}", medicalServiceCategoryId);
        medicalServiceCategoryRepository.delete(medicalServiceCategory);
    }
}

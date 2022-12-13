package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateMedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.exception.notfound.MedicalServiceCategoryNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.MedicalServiceNotFoundException;
import ru.itis.hauntedo.simbirtest.model.MedicalService;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;
import ru.itis.hauntedo.simbirtest.repository.MedicalServiceCategoryRepository;
import ru.itis.hauntedo.simbirtest.repository.MedicalServiceRepository;
import ru.itis.hauntedo.simbirtest.service.MedicalServiceService;
import ru.itis.hauntedo.simbirtest.utils.mapper.MedicalServiceMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalServiceServiceImpl implements MedicalServiceService {


    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalServiceMapper medicalServiceMapper;
    private final MedicalServiceCategoryRepository medicalServiceCategoryRepository;

    @Override
    public void deleteMedicalServiceById(UUID medicalServiceId) {
        log.info("Find medical service by Id: {}", medicalServiceId);
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(MedicalServiceNotFoundException::new);
        log.info("Delete medical service by Id: {}", medicalServiceId);
        medicalServiceRepository.delete(medicalService);
    }

    @Override
    public MedicalServiceResponse addMedicalService(MedicalServiceRequest medicalServiceRequest) {
        MedicalService newMedicalService = medicalServiceMapper.toEntity(medicalServiceRequest);
        log.info("Find medical service category by Id: {}", medicalServiceRequest.getMedicalServiceCategoryId());
        MedicalServiceCategory category = medicalServiceCategoryRepository
                .findById(medicalServiceRequest.getMedicalServiceCategoryId())
                .orElseThrow(MedicalServiceCategoryNotFoundException::new);
        newMedicalService.setMedicalServiceCategory(category);
        log.info("Save medical service");
        return medicalServiceMapper.toResponse(medicalServiceRepository.save(newMedicalService));
    }

    @Override
    public MedicalServiceResponse getMedicalServiceById(UUID medicalServiceId) {
        log.info("Find medical service by Id: {}", medicalServiceId);
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(MedicalServiceNotFoundException::new);
        return medicalServiceMapper.toResponse(medicalService);
    }

    @Override
    public PageResponse<MedicalServiceResponse> getMedicalServices(int page, int size) {
        log.info("Get list of medical services");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MedicalService> result = medicalServiceRepository.findAll(pageRequest);
        return PageResponse.<MedicalServiceResponse>builder()
                .content(medicalServiceMapper.toList(result.getContent()))
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public MedicalServiceResponse updateMedicalServiceById(UpdateMedicalServiceRequest medicalServiceRequest, UUID medicalServiceId) {
        log.info("Find medical service by Id: {}", medicalServiceId);
        MedicalService medicalService = medicalServiceRepository.findById(medicalServiceId)
                .orElseThrow(MedicalServiceNotFoundException::new);
        medicalServiceMapper.updateMedicalService(medicalService, medicalServiceRequest);
        log.info("Update medical service");
        return medicalServiceMapper.toResponse(medicalServiceRepository.save(medicalService));
    }
}

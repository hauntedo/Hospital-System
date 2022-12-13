package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateMedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;

import java.util.UUID;

//название не очень))
public interface MedicalServiceService {
    void deleteMedicalServiceById(UUID medicalServiceId);

    MedicalServiceResponse addMedicalService(MedicalServiceRequest medicalServiceRequest);

    MedicalServiceResponse getMedicalServiceById(UUID medicalServiceId);

    PageResponse<MedicalServiceResponse> getMedicalServices(int page, int size);

    MedicalServiceResponse updateMedicalServiceById(UpdateMedicalServiceRequest medicalServiceRequest, UUID medicalServiceId);
}

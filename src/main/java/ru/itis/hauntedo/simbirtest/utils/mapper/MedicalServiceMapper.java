package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateMedicalServiceRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceResponse;
import ru.itis.hauntedo.simbirtest.model.MedicalService;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalServiceMapper {

    MedicalServiceResponse toResponse(MedicalService medicalService);
    MedicalService toEntity(MedicalServiceRequest medicalServiceRequest);
    void updateMedicalService(@MappingTarget MedicalService medicalService, UpdateMedicalServiceRequest medicalServiceRequest);
    List<MedicalServiceResponse> toList(List<MedicalService> medicalServices);

}

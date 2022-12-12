package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itis.hauntedo.simbirtest.dto.request.MedicalServiceCategoryRequest;
import ru.itis.hauntedo.simbirtest.dto.response.MedicalServiceCategoryResponse;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalServiceCategoryMapper {

    MedicalServiceCategoryResponse toMedicalServiceCategoryResponse(MedicalServiceCategory medicalServiceCategory);

    MedicalServiceCategory toMedicalServiceCategory(MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    void updateMedicalServiceCategory(
            @MappingTarget MedicalServiceCategory medicalServiceCategory, MedicalServiceCategoryRequest medicalServiceCategoryRequest);

    List<MedicalServiceCategoryResponse> toList(List<MedicalServiceCategory> medicalServiceCategories);

}

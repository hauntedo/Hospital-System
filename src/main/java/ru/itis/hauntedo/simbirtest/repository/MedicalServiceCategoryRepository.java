package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.MedicalServiceCategory;

import java.util.UUID;

public interface MedicalServiceCategoryRepository extends JpaRepository<MedicalServiceCategory, UUID> {

    boolean existsByTitle(String medicalServiceCategoryTitle);
}

package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.MedicalService;

import java.util.UUID;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, UUID> {
}

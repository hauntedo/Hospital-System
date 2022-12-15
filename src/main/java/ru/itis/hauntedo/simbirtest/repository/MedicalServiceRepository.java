package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.hauntedo.simbirtest.model.MedicalService;

import java.util.UUID;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, UUID> {

    @Query(nativeQuery = true,
    value = "select case when(count(*)>0) then true else false end " +
            "from medical_service_doctor where doctor_id = :doctorId and medical_service_id = :medicalServiceId")
    boolean existsByDoctorId(@Param("doctorId") UUID doctorId, @Param("medicalServiceId") UUID medicalServiceId);


}

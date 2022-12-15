package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.hauntedo.simbirtest.model.MedicalService;

import java.util.UUID;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, UUID>, JpaSpecificationExecutor<MedicalService> {

    @Query(nativeQuery = true,
    value = "select case when(count(*)>0) then true else false end " +
            "from medical_service_doctor where doctor_id = :doctorId and medical_service_id = :medicalServiceId")
    boolean existsByDoctorId(@Param("doctorId") UUID doctorId, @Param("medicalServiceId") UUID medicalServiceId);

    Page<MedicalService> findAllByMedicalServiceCategory_Code(String medicalServiceCategory_code, Pageable pageable);


}

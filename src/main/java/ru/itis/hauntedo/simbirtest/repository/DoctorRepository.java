package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itis.hauntedo.simbirtest.model.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {

    boolean existsByEmailAndPhone(String email, String phone);

}

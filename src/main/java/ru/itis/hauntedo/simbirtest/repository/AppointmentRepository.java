package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.itis.hauntedo.simbirtest.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {

    Optional<Appointment> findByDoctor_Id(UUID doctorId);
    List<Appointment> findAllByDoctor_Id(UUID doctorId);

    Page<Appointment> findAllByDateAndPatient_Id(LocalDate date, UUID doctor_id, Pageable pageable);

    Page<Appointment> findAllByPatient_Id(UUID patient_id, Pageable pageable, Specification<Appointment> specification);
    Page<Appointment> findAllByDoctor_Id(UUID doctor_id, Pageable pageable, Specification<Appointment> specification);

    List<Appointment> findAllByDateAndDoctor_Id(LocalDate date, UUID doctor_id);

    Page<Appointment> findAllByDateBeforeAndStartBeforeAndPatient_Id(LocalDate date, LocalTime start, UUID patient_id, Pageable pageable);
    Page<Appointment> findAllByDateAndStartAndPatient_Id(LocalDate date, LocalTime start, UUID patient_id, Pageable pageable);
    Page<Appointment> findAllByDateAfterAndStartAndPatient_Id(LocalDate date, LocalTime start, UUID patient_id, Pageable pageable);

}

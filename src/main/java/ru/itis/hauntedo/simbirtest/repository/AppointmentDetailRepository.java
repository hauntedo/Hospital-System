package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.AppointmentDetail;

import java.util.Optional;
import java.util.UUID;

public interface AppointmentDetailRepository extends JpaRepository<AppointmentDetail, UUID> {

    Optional<AppointmentDetail> findByAppointment_Id(UUID appointment_id);

}

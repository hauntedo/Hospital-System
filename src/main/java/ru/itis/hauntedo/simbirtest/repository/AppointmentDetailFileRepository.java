package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.AppointmentDetailFile;

import java.util.UUID;

public interface AppointmentDetailFileRepository extends JpaRepository<AppointmentDetailFile, UUID> {
}

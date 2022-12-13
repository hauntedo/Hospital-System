package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.FileEntity;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
}

package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.ConfirmCode;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, UUID> {

    Optional<ConfirmCode> findOneByUserId(UUID userID);
    Optional<ConfirmCode> findOneByConfirmCode(UUID confirmCode);
}

package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.RefreshToken;

import java.util.UUID;

public interface UserRefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}

package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.UserAvatar;

import java.util.Optional;
import java.util.UUID;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, UUID> {

    Optional<UserAvatar> findByUserId(UUID userId);
}

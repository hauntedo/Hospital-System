package ru.itis.hauntedo.simbirtest.service;

import java.util.UUID;

public interface UserAvatarService {

    UUID getPhoto(UUID userId);

    UUID saveUserPhoto(UUID userId, UUID fileId);
}

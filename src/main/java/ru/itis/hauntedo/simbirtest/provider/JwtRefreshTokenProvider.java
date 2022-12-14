package ru.itis.hauntedo.simbirtest.provider;

import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.model.RefreshToken;

import java.util.UUID;

public interface JwtRefreshTokenProvider {

    UUID generateRefreshToken(UserResponse userResponse);

    RefreshToken verifyRefreshTokenExpiration(UUID refreshToken);
}

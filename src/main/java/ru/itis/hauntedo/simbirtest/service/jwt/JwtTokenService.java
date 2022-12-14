package ru.itis.hauntedo.simbirtest.service.jwt;

import ru.itis.hauntedo.simbirtest.dto.TokenPairRequest;
import ru.itis.hauntedo.simbirtest.dto.TokenPairResponse;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;

public interface JwtTokenService {
    UserResponse getUserByToken(String token);

    TokenPairResponse generateTokenCouple(UserResponse accountResponse);

    TokenPairResponse refreshAccessToken(TokenPairRequest tokenPairRequest);

    String getValidToken(String token);
}

package ru.itis.hauntedo.simbirtest.provider.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.exception.notfound.UserNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.RefreshTokenNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.TokenRefreshException;
import ru.itis.hauntedo.simbirtest.model.RefreshToken;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.provider.JwtRefreshTokenProvider;
import ru.itis.hauntedo.simbirtest.repository.UserRefreshTokenRepository;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenProviderImpl implements JwtRefreshTokenProvider {

    @Value("${jwt.expiration.refresh.mills}")
    private Long expirationRefreshInMills;

    private final UserRefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public UUID generateRefreshToken(UserResponse userResponse) {
        User user = userRepository.findById(userResponse.getId())
                .orElseThrow(UserNotFoundException::new);
        RefreshToken refreshToken = RefreshToken.builder()
                .account(user)
                .expiryDate(Instant.now().plusMillis(expirationRefreshInMills))
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getId();
    }

    @Override
    public RefreshToken verifyRefreshTokenExpiration(UUID refreshTokenId) {
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        refreshTokenRepository.delete(refreshToken);

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(String.valueOf(refreshToken.getId()), "Refresh token expired.");
        }

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .expiryDate(Instant.now().plusMillis(expirationRefreshInMills))
                        .account(refreshToken.getAccount())
                        .build());
    }
}

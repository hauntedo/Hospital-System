package ru.itis.hauntedo.simbirtest.provider;

import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.exception.unauthorized.AuthenticationHeaderException;

import java.util.Date;
import java.util.Map;

public interface JwtAccessTokenProvider {

    String generateAccessToken(String subject, Map<String, Object> data);

    boolean validateAccessToken(String accessToken) throws AuthenticationHeaderException;

    UserResponse getUserByToken(String token) throws AuthenticationHeaderException;

    String getRoleFromAccessToken(String accessToken);

    Date getExpirationDateFromAccessToken(String accessToken);

    String getSubjectFromAccessToken(String accessToken);

}

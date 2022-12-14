package ru.itis.hauntedo.simbirtest.exception.unauthorized;

import org.springframework.http.HttpStatus;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;

public class TokenRefreshException extends GlobalServiceException {
    public TokenRefreshException(String token, String message) {
        super(HttpStatus.UNAUTHORIZED, String.format("Failed for [%s]: %s", token, message));
    }
}

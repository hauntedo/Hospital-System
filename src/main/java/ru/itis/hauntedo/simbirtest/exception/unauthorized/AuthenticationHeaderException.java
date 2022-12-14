package ru.itis.hauntedo.simbirtest.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationHeaderException extends AuthenticationException {

    public AuthenticationHeaderException(String message) {
        super(message);
    }

}

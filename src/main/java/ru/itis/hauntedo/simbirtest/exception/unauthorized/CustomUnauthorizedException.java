package ru.itis.hauntedo.simbirtest.exception.unauthorized;

import org.springframework.http.HttpStatus;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;

public class CustomUnauthorizedException extends GlobalServiceException {

    public CustomUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

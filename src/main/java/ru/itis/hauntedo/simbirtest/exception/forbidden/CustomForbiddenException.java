package ru.itis.hauntedo.simbirtest.exception.forbidden;

import org.springframework.http.HttpStatus;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;

public class CustomForbiddenException extends GlobalServiceException {

    public CustomForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}

package ru.itis.hauntedo.simbirtest.exception.badrequest;

import org.springframework.http.HttpStatus;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;

public class BadRequestException extends GlobalServiceException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}


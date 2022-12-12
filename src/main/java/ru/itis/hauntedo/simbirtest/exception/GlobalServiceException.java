package ru.itis.hauntedo.simbirtest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GlobalServiceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

}

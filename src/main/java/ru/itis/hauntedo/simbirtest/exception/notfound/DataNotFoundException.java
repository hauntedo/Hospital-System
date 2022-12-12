package ru.itis.hauntedo.simbirtest.exception.notfound;

import org.springframework.http.HttpStatus;
import ru.itis.hauntedo.simbirtest.exception.GlobalServiceException;

public class DataNotFoundException extends GlobalServiceException {

    public DataNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

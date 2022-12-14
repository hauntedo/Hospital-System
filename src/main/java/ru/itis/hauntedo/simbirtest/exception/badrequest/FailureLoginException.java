package ru.itis.hauntedo.simbirtest.exception.badrequest;

public class FailureLoginException extends BadRequestException {
    public FailureLoginException() {
        super("Incorrect data");
    }
}

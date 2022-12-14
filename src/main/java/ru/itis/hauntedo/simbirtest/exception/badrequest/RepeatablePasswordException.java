package ru.itis.hauntedo.simbirtest.exception.badrequest;

public class RepeatablePasswordException extends BadRequestException {

    public RepeatablePasswordException() {
        super("Passwords dont match");
    }

    public RepeatablePasswordException(String message) {
        super(message);
    }
}

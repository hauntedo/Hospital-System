package ru.itis.hauntedo.simbirtest.exception.badrequest;

public class UnavailableTimeTableException extends BadRequestException {
    public UnavailableTimeTableException() {
        super("Unavailable time table");
    }

    public UnavailableTimeTableException(String message) {
        super(message);
    }
}

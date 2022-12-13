package ru.itis.hauntedo.simbirtest.exception.badrequest;

public class BadConfirmationException extends BadRequestException {
    public BadConfirmationException(String confirmation_failure) {
        super(confirmation_failure);
    }

    public BadConfirmationException() {
        super("Bad confirmation");
    }
}

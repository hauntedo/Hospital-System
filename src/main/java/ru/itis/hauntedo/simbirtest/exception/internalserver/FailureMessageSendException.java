package ru.itis.hauntedo.simbirtest.exception.internalserver;

public class FailureMessageSendException extends InternalServerException {

    public FailureMessageSendException(String message) {
        super(message);
    }
}

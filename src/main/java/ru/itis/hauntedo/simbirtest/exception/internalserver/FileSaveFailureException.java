package ru.itis.hauntedo.simbirtest.exception.internalserver;

public class FileSaveFailureException extends InternalServerException {

    public FileSaveFailureException() {
        super("File saving error");
    }

    public FileSaveFailureException(String message) {
        super(message);
    }
}

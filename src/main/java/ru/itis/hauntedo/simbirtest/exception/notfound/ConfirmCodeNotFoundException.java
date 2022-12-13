package ru.itis.hauntedo.simbirtest.exception.notfound;

public class ConfirmCodeNotFoundException extends DataNotFoundException {
    public ConfirmCodeNotFoundException(String message) {
        super(message);
    }

    public ConfirmCodeNotFoundException() {
        super("Confirm code not found");
    }
}

package ru.itis.hauntedo.simbirtest.exception.notfound;

public class UserNotFoundException extends DataNotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("User not found");
    }
}

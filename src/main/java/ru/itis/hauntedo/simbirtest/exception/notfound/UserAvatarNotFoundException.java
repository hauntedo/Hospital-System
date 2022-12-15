package ru.itis.hauntedo.simbirtest.exception.notfound;

public class UserAvatarNotFoundException extends DataNotFoundException {
    public UserAvatarNotFoundException() {
        super("User avatar not found");
    }

    public UserAvatarNotFoundException(String message) {
        super(message);
    }
}

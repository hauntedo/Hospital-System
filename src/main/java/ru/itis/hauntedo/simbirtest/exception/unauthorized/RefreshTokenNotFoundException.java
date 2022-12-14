package ru.itis.hauntedo.simbirtest.exception.unauthorized;

public class RefreshTokenNotFoundException extends CustomUnauthorizedException {
    public RefreshTokenNotFoundException() {
        super("Refresh token not found");
    }

}

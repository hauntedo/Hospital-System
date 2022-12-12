package ru.itis.hauntedo.simbirtest.exception.badrequest;

public class OccupiedDataException extends BadRequestException {

    public OccupiedDataException(String message) {
        super(message);
    }

    public OccupiedDataException() {
        super("Data is occupied");
    }
}

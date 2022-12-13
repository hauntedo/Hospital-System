package ru.itis.hauntedo.simbirtest.exception.notfound;

public class DoctorNotFoundException extends DataNotFoundException{

    public DoctorNotFoundException() {
        super("Doctor not found");
    }

    public DoctorNotFoundException(String message) {
        super(message);
    }
}

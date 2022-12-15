package ru.itis.hauntedo.simbirtest.exception.notfound;

public class AppointmentNotFoundException extends DataNotFoundException {
    public AppointmentNotFoundException() {
        super("Appointment not found");
    }

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}

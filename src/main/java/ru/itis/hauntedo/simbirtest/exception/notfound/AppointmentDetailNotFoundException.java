package ru.itis.hauntedo.simbirtest.exception.notfound;

public class AppointmentDetailNotFoundException extends DataNotFoundException {
    public AppointmentDetailNotFoundException() {
        super("Appointment details not found");
    }

    public AppointmentDetailNotFoundException(String message) {
        super(message);
    }
}

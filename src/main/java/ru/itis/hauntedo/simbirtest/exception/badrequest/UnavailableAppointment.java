package ru.itis.hauntedo.simbirtest.exception.badrequest;

import ru.itis.hauntedo.simbirtest.exception.notfound.DataNotFoundException;

public class UnavailableAppointment extends DataNotFoundException {

    public UnavailableAppointment() {
        super("Unavailable appointment");
    }

    public UnavailableAppointment(String s) {
        super(s);
    }
}

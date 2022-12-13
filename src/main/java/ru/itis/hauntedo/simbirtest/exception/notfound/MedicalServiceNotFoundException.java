package ru.itis.hauntedo.simbirtest.exception.notfound;


public class MedicalServiceNotFoundException extends DataNotFoundException{

    public MedicalServiceNotFoundException() {
        super("Medical service not found");
    }

    public MedicalServiceNotFoundException(String message) {
        super(message);
    }
}

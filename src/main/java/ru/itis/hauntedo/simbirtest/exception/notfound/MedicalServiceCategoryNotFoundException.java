package ru.itis.hauntedo.simbirtest.exception.notfound;

public class MedicalServiceCategoryNotFoundException extends DataNotFoundException{

    public MedicalServiceCategoryNotFoundException() {
        super("Medical service category not found");
    }
    public MedicalServiceCategoryNotFoundException(String message) {
        super(message);
    }
}

package ru.itis.hauntedo.simbirtest.exception.notfound;

public class FileNotFoundException extends DataNotFoundException {

    public FileNotFoundException() {
        super("File not found");
    }
}

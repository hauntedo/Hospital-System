package ru.itis.hauntedo.simbirtest.service;

import java.util.Map;

public interface EmailService {

    void send(Map<String, String> emailData, String subject, String operation);
    String generateTemplateUsingFreemarker(String firstName, String confirmCode);

}
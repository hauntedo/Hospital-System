package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.model.ConfirmCode;
import ru.itis.hauntedo.simbirtest.model.User;

import java.util.UUID;

public interface ConfirmService {

    void createConfirmCode(User user);
    void updateConfirmCode(User user);

    ConfirmCode getConfirmCode(UUID confirmCode);
}

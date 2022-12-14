package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.dto.request.*;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse getUserById(UUID id);

    UUID createUser(CreateUserRequest createUserRequest);

    UserResponse login(UserRequest userRequest);

    UserResponse findBySubject(String subject);

    UserResponse updateUser(UUID userId, UpdateUserRequest userRequest);

    void updatePassword(UUID userId, UpdatePasswordRequest passwordRequest);

    UUID confirmUser(UUID confirmCode);

    UserResponse createPassword(UUID userId, UserPasswordRequest passwordRequest);
}

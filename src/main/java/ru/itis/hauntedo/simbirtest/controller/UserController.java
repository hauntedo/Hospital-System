package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.UserApi;
import ru.itis.hauntedo.simbirtest.dto.request.CreateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdatePasswordRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<GridFsResource> getPhotoByUserId(UUID userId) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<UUID> saveUserPhoto(UUID userId, UUID fileID) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UUID userId, UpdateUserRequest userRequest) {
        return ResponseEntity.status(201).body(userService.updateUser(userId, userRequest));
    }

    @Override
    public ResponseEntity<UUID> updateUserPhoto(UUID userId, UUID fileId) {
        return null;
    }

    @Override
    public ResponseEntity<UUID> registration(CreateUserRequest createUserRequest) {
        return ResponseEntity.status(201).body(userService.createUser(createUserRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> updateUserPassword(UUID userId, UpdatePasswordRequest passwordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UUID> confirm(UUID confirmCode) {
        return ResponseEntity.ok(userService.confirmUser(confirmCode));
    }
}

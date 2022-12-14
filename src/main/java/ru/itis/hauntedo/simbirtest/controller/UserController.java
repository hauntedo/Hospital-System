package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.UserApi;
import ru.itis.hauntedo.simbirtest.dto.TokenPairResponse;
import ru.itis.hauntedo.simbirtest.dto.request.*;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.service.UserService;
import ru.itis.hauntedo.simbirtest.service.jwt.JwtTokenService;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

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
        userService.updatePassword(userId, passwordRequest);
        return ResponseEntity.status(201)
                .body(
                        SuccessResponse.builder()
                                .message("Password updated successfully")
                                .time(Instant.now())
                                .build()
                );
    }

    @Override
    public ResponseEntity<UUID> confirm(UUID confirmCode) {
        return ResponseEntity.ok(userService.confirmUser(confirmCode));
    }

    @Override
    public ResponseEntity<TokenPairResponse> createPassword(UUID userId, UserPasswordRequest passwordRequest) {
        return ResponseEntity.ok(
                jwtTokenService.generateTokenCouple(
                        userService.createPassword(userId, passwordRequest)));
    }

    @Override
    public ResponseEntity<TokenPairResponse> login(UserRequest userRequest) {
        return ResponseEntity.ok(jwtTokenService.generateTokenCouple(userService.login(userRequest)));
    }
}

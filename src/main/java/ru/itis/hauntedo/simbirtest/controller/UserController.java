package ru.itis.hauntedo.simbirtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.hauntedo.simbirtest.api.UserApi;
import ru.itis.hauntedo.simbirtest.dto.TokenPairResponse;
import ru.itis.hauntedo.simbirtest.dto.request.*;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.model.FileEntity;
import ru.itis.hauntedo.simbirtest.service.AppointmentService;
import ru.itis.hauntedo.simbirtest.service.FileService;
import ru.itis.hauntedo.simbirtest.service.UserAvatarService;
import ru.itis.hauntedo.simbirtest.service.UserService;
import ru.itis.hauntedo.simbirtest.service.jwt.JwtTokenService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final FileService fileService;
    private final UserAvatarService userAvatarService;
    private final AppointmentService appointmentService;

    @Override
    public ResponseEntity<GridFsResource> getPhotoByUserId(UUID userId) {
        FileEntity file = fileService.getFile(userAvatarService.getPhoto(userId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "filename=\"" + file.getOriginalFileName() + "\"")
                .body(fileService.getFileFromStorage(file.getObjectId()));
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<UUID> saveUserPhoto(UUID userId, UUID fileID) {
        return ResponseEntity
                .status(201)
                .body(userAvatarService.saveUserPhoto(userId, fileID));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UUID userId, UpdateUserRequest userRequest) {
        return ResponseEntity.status(201).body(userService.updateUser(userId, userRequest));
    }

    @Override
    public ResponseEntity<UUID> updateUserPhoto(UUID userId, UUID fileId) {
        return ResponseEntity.ok(userAvatarService.saveUserPhoto(userId, fileId));
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
    public ResponseEntity<PageResponse<AppointmentResponse>> getAppointments(UUID userId, int size, int page, LocalTime start, String op, LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAvailableAppointmentByUser(userId, size, page, start, op, date));
    }

    @Override
    public ResponseEntity<TokenPairResponse> login(UserRequest userRequest) {
        return ResponseEntity.ok(jwtTokenService.generateTokenCouple(userService.login(userRequest)));
    }
}

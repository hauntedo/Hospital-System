package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.hauntedo.simbirtest.dto.request.*;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadConfirmationException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadDataException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.FailureLoginException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.OccupiedDataException;
import ru.itis.hauntedo.simbirtest.exception.notfound.UserNotFoundException;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;
import ru.itis.hauntedo.simbirtest.service.ConfirmService;
import ru.itis.hauntedo.simbirtest.service.UserService;
import ru.itis.hauntedo.simbirtest.utils.enums.Role;
import ru.itis.hauntedo.simbirtest.utils.enums.State;
import ru.itis.hauntedo.simbirtest.utils.mapper.UserMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final ConfirmService confirmService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUserById(UUID id) {
        log.info("Find user by id: {}", id);
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    @Override
    public UUID createUser(CreateUserRequest createUserRequest) {
        log.info("Check email {}, phone {}", createUserRequest.getEmail(), createUserRequest.getPhone());
        if (userRepository.existsByEmailAndPhone(createUserRequest.getEmail(), createUserRequest.getPhone())) {
            log.error("Email or phone are occupied {} : {}", createUserRequest.getEmail(), createUserRequest.getPhone());
            throw new OccupiedDataException("Email or phone are occupied");
        }
        User newUser = userMapper.toUserEntity(createUserRequest);
        newUser.setState(State.NOT_CONFIRMED);
        newUser.setRole(Role.PATIENT);
        User savedUser = userRepository.save(newUser);
        confirmService.createConfirmCode(savedUser);
        return savedUser.getId();
    }

    @Override
    public UserResponse login(UserRequest userRequest) {
        return userRepository.findOneByEmail(userRequest.getEmail())
                .filter(user -> passwordEncoder.matches(userRequest.getPassword(), user.getHashPassword()))
                .map(userMapper::toUserResponse)
                .orElseThrow(FailureLoginException::new);
    }


    @Override
    public UserResponse findBySubject(String subject) {
        return userMapper.toUserResponse(userRepository.findOneByEmail(subject)
                .orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserResponse updateUser(UUID userId, UpdateUserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userMapper.toUserEntity(userRequest, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void updatePassword(UUID userId, UpdatePasswordRequest passwordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (passwordEncoder.matches(passwordRequest.getOldPassword(), user.getHashPassword())) {
            if (passwordRequest.getNewPassword().equals(passwordRequest.getRepeatPassword())) {
                user.setHashPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new BadDataException("Repeat and new passwords dont match");
            }
        } else {
            throw new BadDataException("Old password doesnt match");
        }
    }

    @Transactional
    @Override
    public UUID confirmUser(UUID confirmCode) {
        User user = confirmService.getConfirmCode(confirmCode).getUser();
        if (user != null) {
            user.setState(State.CONFIRMED);
            confirmService.updateConfirmCode(user);
            return userRepository.save(user).getId();
        } else {
            throw new BadConfirmationException("Confirmation failure");
        }
    }

    @Override
    public UserResponse createPassword(UUID userId, UserPasswordRequest passwordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (user.getState().name().equals("CONFIRMED")){
            if (passwordRequest.getPassword().equals(passwordRequest.getRepeatPassword())) {
                user.setHashPassword(passwordEncoder.encode(passwordRequest.getPassword()));
                return userMapper.toUserResponse(userRepository.save(user));
            } else {
                throw new BadDataException("Repeat and new password dont match");
            }
        } else {
            throw new BadConfirmationException("Account not confirmed");
        }
    }
}

package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadFileException;
import ru.itis.hauntedo.simbirtest.exception.notfound.UserAvatarNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.notfound.UserNotFoundException;
import ru.itis.hauntedo.simbirtest.model.FileEntity;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.model.UserAvatar;
import ru.itis.hauntedo.simbirtest.repository.UserAvatarRepository;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;
import ru.itis.hauntedo.simbirtest.service.FileService;
import ru.itis.hauntedo.simbirtest.service.UserAvatarService;
import ru.itis.hauntedo.simbirtest.utils.enums.FileType;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    private final FileService fileService;
    private final UserRepository userRepository;

    private final UserAvatarRepository userAvatarRepository;

    @Override
    public UUID getPhoto(UUID userId) {
        UserAvatar userAvatar = userAvatarRepository.findByUserId(userId)
                .orElseThrow(UserAvatarNotFoundException::new);
        return userAvatar.getUserAvatar().getId();
    }

    @Override
    public UUID saveUserPhoto(UUID userId, UUID fileId) {
        FileEntity file = fileService.getFile(fileId);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (file.getFileType() != FileType.USER_PHOTO) {
            throw new BadFileException("Incorrect file type");
        }
        UserAvatar userAvatar = userAvatarRepository.findByUserId(userId)
                .map(userAvatarEntity -> {
                    userAvatarEntity.setUserAvatar(file);
                    return userAvatarEntity;
                })
                .orElse(
                        UserAvatar.builder()
                                .userAvatar(file)
                                .user(user)
                                .build());
        return userAvatarRepository.save(userAvatar).getId();
    }
}

package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.exception.notfound.ConfirmCodeNotFoundException;
import ru.itis.hauntedo.simbirtest.model.ConfirmCode;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.repository.ConfirmCodeRepository;
import ru.itis.hauntedo.simbirtest.service.ConfirmService;
import ru.itis.hauntedo.simbirtest.service.EmailService;
import ru.itis.hauntedo.simbirtest.utils.enums.ConfirmCodeState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.itis.hauntedo.simbirtest.utils.consts.ApiConst.EMAIL_CONFIRM;
import static ru.itis.hauntedo.simbirtest.utils.enums.ConfirmCodeState.EXPIRED;
import static ru.itis.hauntedo.simbirtest.utils.enums.ConfirmCodeState.NOT_EXPIRED;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConfirmServiceImpl implements ConfirmService {


    private final ConfirmCodeRepository confirmCodeRepository;
    private final EmailService emailService;

    @Override
    public void createConfirmCode(User user) {
        ConfirmCode confirmCode = ConfirmCode.builder()
                .user(user)
                .confirmCode(UUID.randomUUID())
                .confirmCodeState(NOT_EXPIRED)
                .build();
        Map<String, String> emailData = new HashMap<>();
        emailData.put("user_email", user.getEmail());
        emailData.put("user_firstname", user.getFirstName());
        emailData.put("confirm_code", confirmCode.getConfirmCode().toString());
        new Thread(() -> emailService.send(emailData, "Confirm account", EMAIL_CONFIRM)).start();
        confirmCodeRepository.save(confirmCode);
    }

    @Override
    public void updateConfirmCode(User user) {
        Optional<ConfirmCode> confirmCode = confirmCodeRepository.findOneByUserId(user.getId());
        if (confirmCode.isPresent()) {
            if (confirmCode.get().getConfirmCodeState() == EXPIRED) {
                UUID newConfirmCode = UUID.randomUUID();
                confirmCode.get().setConfirmCode(newConfirmCode);
                confirmCode.get().setConfirmCodeState(NOT_EXPIRED);
            } else {
                confirmCode.get().setConfirmCodeState(EXPIRED);
            }
            confirmCodeRepository.save(confirmCode.get());
        } else {
            throw new ConfirmCodeNotFoundException();
        }
    }

    @Override
    public ConfirmCode getConfirmCode(UUID confirmCode) {
        return confirmCodeRepository.findOneByConfirmCode(confirmCode)
                .orElseThrow(ConfirmCodeNotFoundException::new);
    }
}

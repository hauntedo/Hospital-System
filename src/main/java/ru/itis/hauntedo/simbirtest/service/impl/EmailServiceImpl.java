package ru.itis.hauntedo.simbirtest.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ru.itis.hauntedo.simbirtest.exception.internalserver.FailureMessageSendException;
import ru.itis.hauntedo.simbirtest.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${confirm.url.prefix}")
    private String confirmUrlPrefix;

    @Value("${mail.template}")
    private String template;

    @Value("${spring.mail.username}")
    private String from;

    private final FreeMarkerConfigurer freemarkerConfigurer;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(FreeMarkerConfigurer freemarkerConfigurer, JavaMailSender mailSender) {
        this.freemarkerConfigurer = freemarkerConfigurer;
        this.mailSender = mailSender;
    }

    public void send(Map<String, String> emailData, String subject) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setFrom(from);
            helper.setTo(emailData.get("user_email"));
            helper.setSubject(subject);
            mimeMessage.setContent(generateTemplateUsingFreemarker(emailData.get("user_firstname"),
                    emailData.get("confirm_code")), "text/html");
            log.info("Sending message by mail {}", emailData.get("user_email"));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Fail sending message: {}", e.getMessage());
            throw new FailureMessageSendException(e.getMessage());
        }
    }

    public String generateTemplateUsingFreemarker(String firstName, String confirmCode) {

        try {
            Map<String, Object> content = new HashMap<>();
            content.put("action_link", confirmUrlPrefix + confirmCode);
            content.put("first_name", firstName);
            Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(template);
            log.info("Generate message template");
            return FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerTemplate, content);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
            throw new FailureMessageSendException(e.getMessage());
        }
    }

}

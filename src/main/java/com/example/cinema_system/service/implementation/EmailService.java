package com.example.cinema_system.service.implementation;

import com.example.cinema_system.logger.Logger;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender; // must be (maven dependency, application.properties, @SpringBootApplication)
    private final Logger securityLogger;

    @Value("kachmar.developer@gmail.com")
    private String from;

    public void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Email Verification";
        String path = "/verify";
        String massage = "Click the button below to verify your email address: ";
        String templateName = "email/verification";
        sendEmail(email, verificationToken, subject, path, massage, templateName);
    }

    public void sendForgotPasswordEmail(String email, String resetToken) {
        String subject = "Password Reset Request";
        String path = "/req/reset-password";
        String massage = "Click the button below to reset your password: ";
        String templateName = "email/reset_password";
        sendEmail(email, resetToken, subject, path, massage, templateName);
    }

    public void sendVerificationCodeEmail(String email, String code) {
        String subject = "Your verification code";
        String path = "/verify"; // page where user can enter code
        String message = "Use the code below to verify your email:";
        String templateName = "email/verification_code";

        try {
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .toUriString();
            Context contextForThymeleaf = new Context(); // for thymeleaf
            contextForThymeleaf.setVariables(Map.of(
                    "subject", subject,
                    "message", message,
                    "code", code,
                    "actionUrl", actionUrl
            ));

            String context = templateEngine.process(templateName, contextForThymeleaf);
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(context, true); // true = HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            securityLogger.logEmailSendError(email);
            throw new RuntimeException(e);
        }
    }

    private void sendEmail(String email, String token, String subject, String path, String message, String templateName) {

        try {
            String actionUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .queryParam("token", token)
                    .toUriString();
            Context contextForThymeleaf = new Context(); // for thymeleaf
            contextForThymeleaf.setVariables(Map.of(
                    "subject", subject,
                    "message", message,
                    "actionUrl", actionUrl
            ));

            String context = templateEngine.process(templateName, contextForThymeleaf);
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(context, true); // true = HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            securityLogger.logEmailSendError(email);
            throw new RuntimeException(e);
        }
    }

}

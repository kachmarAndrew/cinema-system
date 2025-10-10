package com.example.cinema_system.service.implementation;

import com.example.cinema_system.logger.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender; // must be (maven dependency, application.properties, @SpringBootApplication)
    private final Logger securityLogger;

    @Async
    public void sendPasswordReset(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kachmar.developer@gmail.com");
        message.setTo(to);
        message.setSubject("Password reset");
        message.setText("Click this link to reset tour password: " +
                "http://localhost:8080/reset-password?token=" + token);
        mailSender.send(message);
        securityLogger.logPasswordRecoveryRequest(to);
    }

}

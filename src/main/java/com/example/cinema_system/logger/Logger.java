package com.example.cinema_system.logger;

import com.example.cinema_system.entity.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Logger {

    public void logLoginSuccess(String email) {
        log.info("User logged in: {}", markEmail(email));
    }

    public void logLoginFailure(String email, String reason) {
        log.warn("Login failed ({}): {}", reason, markEmail(email));
    }

    public void logRegistrationSuccess(String email) {
        log.info("User successfully registered: {}", markEmail(email));
    }

    public void logDeleteSuccess(String email) {
        log.info("User successfully deleted: {}", markEmail(email));
    }

    public void logError(String email, int statusCode, String message) {
        log.info("User {}, got an errorPage with code {}, {}", markEmail(email), statusCode, message);
    }

    public void logPasswordResetSuccess(String email) {
        log.info("Password reset success: {}", markEmail(email));
    }

    public void logRoleSuccessfullyChanged(String email, Role role) {
        log.info("User -> {}, role was successfully changed to: {}", markEmail(email), role);
    }

    // for safety -> {in logs we do not show private information about users}
    private String markEmail(String email) {
        if (email == null) return "unknown";
        int atIndex = email.indexOf("@");
        if (atIndex <= 1) return "***";
        return email.substring(0, Math.min(3, atIndex)) + "***" + email.substring(atIndex);
    }
}

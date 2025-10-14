package com.example.cinema_system.exception.сontrollerAdvice;

import com.example.cinema_system.exception.*;
import com.example.cinema_system.logger.Logger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Logger logger;

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(FilmNotFoundException.class)
    public String handleFilmNotFoundException(FilmNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleOrderNotFoundException(OrderNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public String handleReviewNotFoundException(ReviewNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public String handleSessionNotFoundException(SessionNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public String handleTicketNotFoundException(TicketNotFoundException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException exception, Model model) {
        model.addAttribute("errorMessageKey", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(InsufficientPermissionsException.class)
    public String handleInsufficientPermission(InsufficientPermissionsException ex, Model model, HttpServletRequest request) {
        String user = getCurrentUser();
        logger.logAccessDenied(user, request.getRequestURI());
        model.addAttribute("errorMessageKey", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        logger.logGeneralError(ex);
        model.addAttribute("errorMessageKey", "error.general");
        return "error";
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : "ANONYMOUS";
    }
}

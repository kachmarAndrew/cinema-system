package com.example.cinema_system.controller;

import com.example.cinema_system.entity.enums.ErrorMessage;
import com.example.cinema_system.logger.Logger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController { // TODO: provide .html error template

    private final Logger securityLogger;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model, Principal principal) {

        var status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String email = "Unauthorized";
        if (principal != null) {
            email = principal.getName();
        }

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            ErrorMessage errorMessage = ErrorMessage.fromCode(statusCode);
            securityLogger.logError(email, statusCode, errorMessage.getMessageKey());
            model.addAttribute("statusCode", errorMessage.getErrorCode());
            model.addAttribute("errorMessageKey", errorMessage.getMessageKey());
        }

        return "error";
    }
}

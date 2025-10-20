package com.example.cinema_system.controller;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.service.UserService;
import com.example.cinema_system.service.implementation.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/register")
    public String getRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDTO());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userService.existsByEmail(userDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email is already in use");
            return "auth/register";
        }

        // Generate 6-digit numeric code
        String code = String.format("%06d", new Random().nextInt(1_000_000));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10);

        userDTO.setVerificationToken(code);
        userDTO.setVerificationTokenExpiry(expiry);

        userService.register(userDTO);

        // Send verification code email
        emailService.sendVerificationCodeEmail(userDTO.getEmail(), code);

        // Redirect to verification page with email prefilled
        redirectAttributes.addFlashAttribute("email", userDTO.getEmail());
        redirectAttributes.addFlashAttribute("success", "Registration successful. Please check your email for the 6-digit verification code.");
        return "redirect:/verify";
    }
}

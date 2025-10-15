package com.example.cinema_system.controller;

import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/login") // TODO create a .html template
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/login") // TODO create a .html template
    public String getRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDTO());
        }
        return "auth/register";
    }

    @PostMapping("/register") // TODO create a .html template
    public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userService.existsByEmail(userDTO.getEmail())) {
            bindingResult.rejectValue("userEmail", "error.user", "email already uses some other user");
            return "auth/register";
        }

        userService.register(userDTO);

        redirectAttributes.addFlashAttribute("success", "Registration successful. Please log in.");
        return "redirect:/auth/login";
    }

}

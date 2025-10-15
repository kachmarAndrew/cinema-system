package com.example.cinema_system.controller;

import com.example.cinema_system.dto.FilmDTO;
import com.example.cinema_system.dto.UserDTO;
import com.example.cinema_system.exception.UserNotFoundException;
import com.example.cinema_system.service.FilmService;
import com.example.cinema_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController { // TODO create a .html template for page 'home'

    private final FilmService filmService;
    private final UserService userService;

    @GetMapping("/home")
    public String home(Model model, Principal principal,
                       @PageableDefault(size = 12, sort = "endAt", direction = Sort.Direction.ASC) Pageable pageable,
                       @RequestParam(required = false) String genre,
                       @RequestParam(required = false) String language,
                       @RequestParam(required = false) String status,
                       @RequestParam(required = false) String search) {

        if (status != null && !status.isBlank()) {
            switch (status) { // for some new syntax
                case "SuccessTopUp" -> model.addAttribute("success_message","home.success.top.up");
                case "SuccessPurchase" -> model.addAttribute("success_message", "home.success.purchase");
                case "CartItemAlreadyExist" -> model.addAttribute("error_message", "home.error.cart_item_already_exist");
                case "InsufficientBalance" -> model.addAttribute("error_message", "home.error.insufficient_balance");
            }
        }

        String email = principal.getName();
        UserDTO userDTO = userService.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found Exception"));

        Page<FilmDTO> films = filmService.getAllFilms(pageable, genre, language, search);

        model.addAttribute("user", userDTO);
        model.addAttribute("filmsPage", films);
        model.addAttribute("genre", genre);
        model.addAttribute("language", language);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentPage", films.getNumber());

        Sort.Order order = pageable.getSort().stream().findFirst()
                .orElse(new Sort.Order(Sort.Direction.ASC, "endAt"));

        model.addAttribute("sort", order.getProperty());
        model.addAttribute("direction", order.getDirection().name().toLowerCase());
        model.addAttribute("sortParam", order.getProperty() + "," + order.getDirection().name().toLowerCase());
        return "home";
    }

}

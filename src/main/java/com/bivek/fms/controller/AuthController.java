package com.bivek.fms.controller;

import com.bivek.fms.service.UserService;
import com.bivek.fms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthController {

    private final UserService authService;

    public AuthController(UserService authService) {
        this.authService = authService;
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute User user, Model model) {
        String message = authService.registerUser(user);
        model.addAttribute("message", message);
        return message.contains("successful") ? "login" : "register";
    }

    @GetMapping("login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("login")
    public String loginUser(@ModelAttribute User user, Model model) {
        if (authService.loginUser(user)) {
            return "redirect:/home";
        }
        model.addAttribute("message", "Invalid credentials. Please try again.");
        return "login";
    }
}

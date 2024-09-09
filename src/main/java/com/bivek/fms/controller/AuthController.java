package com.bivek.fms.controller;

import com.bivek.fms.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class AuthController {

    private List<User> users = new ArrayList<>(); // In-memory user storage

    // Show registration form
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Process registration form submission
    @PostMapping("register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Simple validation check
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null
                || user.getPhoneNumber() == null) {
            model.addAttribute("message", "All fields are required.");
            return "register";
        }

        // Add user to the in-memory list
        users.add(user);
        model.addAttribute("message", "Registration successful! Please login.");
        return "login";
    }

    // Show login form
    @GetMapping("login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Process login form submission
    @PostMapping("login")
    public String loginUser(@ModelAttribute User user, Model model) {
        for (User registeredUser : users) {
            if (registeredUser.getUsername().equals(user.getUsername()) &&
                    registeredUser.getPassword().equals(user.getPassword())) {
                model.addAttribute("message", "Login successful!");
                return "redirect:/home"; // Redirect to home page on successful login
            }
        }
        model.addAttribute("message", "Invalid credentials. Please try again.");
        return "login"; // Stay on the login page with an error message on failure
    }
}

package com.bivek.fms.controller;

import com.bivek.fms.entity.User;
import com.bivek.fms.entity.Budget;
import com.bivek.fms.service.BudgetService;
import com.bivek.fms.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class AuthController {
    private final UserService userService;
    private final BudgetService budgetService;
    public AuthController(UserService userService, BudgetService budgetService) {
        this.userService = userService;
        this.budgetService = budgetService;
    }
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("register")
    public String registerUser(@ModelAttribute User user, Model model) {
        String message = userService.registerUser(user);
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
        if (userService.loginUser(user)) {
            return "redirect:/home"; // Redirect to the home page after login
        }
        model.addAttribute("message", "Invalid credentials. Please try again.");
        return "login";
    }
    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal User user, Model model) {
        // Get budgets for the logged-in user
        List<Budget> budgets = budgetService.getBudgetsByUserId(user.getId());
        model.addAttribute("budgets", budgets);
        return "home"; // Return home.html
    }

    @PostMapping("/logout")
    public String logout() {
        // The logout functionality is handled by Spring Security
        return "redirect:/login?logout=true"; // Redirect to login page with logout message
    }
}

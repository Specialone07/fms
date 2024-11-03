package com.bivek.fms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Budget> budgets = budgetService.getBudgetsByUserId(user.getId());
        model.addAttribute("budgets", budgets);
        return "home"; // Returns the home.html template
    }
}
    
package com.bivek.fms.controller;

import com.bivek.fms.entity.Budget;
import com.bivek.fms.entity.User;
import com.bivek.fms.service.BudgetService;
import com.bivek.fms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;
    // Show budget list for the logged-in user
    @GetMapping
    public String listBudgets(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Budget> budgets = budgetService.getBudgetsByUserId(user.getId());
        model.addAttribute("budgets", budgets);
        return "budgets/list"; // Returns the "list.html" template
    }
    // Show form for adding new budget
    @GetMapping("/new")
    public String showCreateBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());
        return "budgets/add"; // Returns the "add.html" template
    }
    // Save a new budget for the logged-in user
    @PostMapping
    public String createBudget(@ModelAttribute Budget budget, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        budget.setUser(user);
        budgetService.createBudget(budget);
        return "redirect:/budgets"; // Redirects to the list of budgets
    }
    // Show form for editing an existing budget
    @GetMapping("/edit/{id}")
    public String showEditBudgetForm(@PathVariable("id") int id, Model model) {
        Budget budget = budgetService.getBudgetById(id);
        model.addAttribute("budget", budget);
        return "budgets/edit"; // Returns the "edit.html" template
    }
    // Update an existing budget
    @PostMapping("/update/{id}")
    public String updateBudget(@PathVariable("id") int id, @ModelAttribute Budget updatedBudget) {
        budgetService.updateBudget(id, updatedBudget);
        return "redirect:/budgets";
    }
    // Delete a budget
    @GetMapping("/delete/{id}")
    public String deleteBudget(@PathVariable("id") int id) {
        budgetService.deleteBudget(id);
        return "redirect:/budgets";
    }
}

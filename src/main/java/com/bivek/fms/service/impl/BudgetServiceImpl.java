package com.bivek.fms.service.impl;

import com.bivek.fms.entity.Budget;
import com.bivek.fms.repository.BudgetRepository;
import com.bivek.fms.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getBudgetsByUserId(int userId) {
        return budgetRepository.findByUserId(userId);
    }

    @Override
    public Budget updateBudget(int id, Budget updatedBudget) {
        Optional<Budget> existingBudget = budgetRepository.findById(id);
        if (existingBudget.isPresent()) {
            Budget budget = existingBudget.get();
            budget.setAmount(updatedBudget.getAmount());
            budget.setCategory(updatedBudget.getCategory());
            budget.setDescription(updatedBudget.getDescription());
            return budgetRepository.save(budget);
        } else {
            throw new RuntimeException("Budget not found");
        }
    }

    @Override
    public void deleteBudget(int id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public Budget getBudgetById(int id) {
        return budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found"));
    }
}

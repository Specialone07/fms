package com.bivek.fms.service;

import com.bivek.fms.entity.Budget;

import java.util.List;

public interface BudgetService {
    Budget createBudget(Budget budget);

    List<Budget> getBudgetsByUserId(int userId);

    Budget updateBudget(int id, Budget updatedBudget);

    void deleteBudget(int id);

    Budget getBudgetById(int id);
}

package com.bivek.fms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.bivek.fms.controller.BudgetController;
import com.bivek.fms.model.Budget;
import com.bivek.fms.service.BudgetService;

public class BudgetControllerUnitTest {

    @Mock
    private BudgetService budgetService;

    @Mock
    private Model model;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Successful Unit Test

    @Test
    public void testCreateBudget_Success() {
        Budget budget = new Budget("Bus Fare", 1000);
        when(budgetService.createBudget(budget)).thenReturn("Budget created successfully");

        String viewName = budgetController.createBudget(budget, model);
        assertEquals("budgetCreated", viewName); // Budget creation success
    }
    @Test
    public void testUpdateBudget_Success() {
        Budget budget = new Budget("Bus Fare", 1200); // Updated amount
        when(budgetService.updateBudget(budget)).thenReturn("Budget updated successfully");
        String viewName = budgetController.updateBudget(budget, model);
        assertEquals("budgetUpdated", viewName); // Budget update success
    }
    @Test
    public void testDeleteBudget_Success() {
        when(budgetService.deleteBudget(1L)).thenReturn("Budget deleted successfully");
        String viewName = budgetController.deleteBudget(1L, model);
        assertEquals("budgetDeleted", viewName); // Budget deletion success
    }

    // Failure Unit Test

    @Test
    public void testCreateBudget_Failure_InvalidData() {
        Budget budget = new Budget("", -100); // Invalid data: empty task name, negative amount
        when(budgetService.createBudget(budget)).thenReturn("Failed to create budget");

        String viewName = budgetController.createBudget(budget, model);
        assertEquals("createBudgetForm", viewName); // Failure: Stays on the create form page
    }
    @Test
    public void testUpdateBudget_Failure_NotFound() {
        Budget budget = new Budget("Bus Fare", 1200); // Budget not found in system
        when(budgetService.updateBudget(budget)).thenReturn("Budget not found");

        String viewName = budgetController.updateBudget(budget, model);
        assertEquals("errorPage", viewName); // Budget not found, shows error page
    }

    @Test
    public void testDeleteBudget_Failure_NotFound() {
        when(budgetService.deleteBudget(99L)).thenReturn("Budget not found");

        String viewName = budgetController.deleteBudget(99L, model); // Non-existent budget
        assertEquals("errorPage", viewName); // Failed deletion, error page shown
    }
}

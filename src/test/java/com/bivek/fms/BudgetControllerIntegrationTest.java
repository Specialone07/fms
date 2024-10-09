package com.bivek.fms;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
@SpringBootTest
@AutoConfigureMockMvc
public class BudgetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Successful Integration
    @Test
    public void testCreateBudget_Success() throws Exception {
        MvcResult result = mockMvc.perform(post("/budget/create")
                .param("task", "Bus Fare")
                .param("amount", "1000"))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals("budgetCreated", result.getModelAndView().getViewName()); // Success: Budget creation
    }
    @Test
    public void testUpdateBudget_Success() throws Exception {
        MvcResult result = mockMvc.perform(put("/budget/update/1")
                .param("task", "Bus Fare")
                .param("amount", "1200"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("budgetUpdated", result.getModelAndView().getViewName()); // Success: Budget update
    }

    @Test
    public void testDeleteBudget_Success() throws Exception {
        mockMvc.perform(delete("/budget/delete/1"))
                .andExpect(status().isNoContent()); // Success: Budget deleted
    }
    // Failure Integration
    @Test
    public void testCreateBudget_Failure_InvalidData() throws Exception {
        MvcResult result = mockMvc.perform(post("/budget/create")
                .param("task", "") // Empty task name
                .param("amount", "-100")) // Negative amount
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("createBudgetForm", result.getModelAndView().getViewName()); // Failure: Creation
    }
    @Test
    public void testUpdateBudget_Failure_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(put("/budget/update/99")
                .param("task", "Buss Fare")
                .param("amount", "1200"))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("errorPage", result.getModelAndView().getViewName()); // Failure: Budget not found
    }
    @Test
    public void testDeleteBudget_Failure_NotFound() throws Exception {
        MvcResult result = mockMvc.perform(delete("/budget/delete/99"))
                .andExpect(status().isNotFound())
                .andReturn();
        assertEquals("errorPage", result.getModelAndView().getViewName()); // Failure: Delete failed
    }
}

package com.bivek.fms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Successful integration test
    @Test
    public void testRegisterUser_Success() throws Exception {
        MvcResult result = mockMvc.perform(post("/register")
                .param("email", "user@gmail.com")
                .param("password", "pass123"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("login", result.getModelAndView().getViewName()); // Registration success redirects to login
    }
    @Test
    public void testLoginUser_Success() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                .param("email", "existinguser@gmail.com")
                .param("password", "password123"))
                .andExpect(status().is3xxRedirection()) // Expecting redirection on successful login
                .andReturn();

        assertEquals("redirect:/home", result.getModelAndView().getViewName()); // Login success redirects to home
    }
    @Test
    public void testShowLoginPage_Success() throws Exception {
        MvcResult result = mockMvc.perform(post("/login"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("login", result.getModelAndView().getViewName()); // Login form should display the login view
    }

    //failure integration test
    @Test
    public void testRegisterUser_Failure_WeakPassword() throws Exception {
        MvcResult result = mockMvc.perform(post("/register")
                .param("email", "failureuser@gmail.com")
                .param("password", "abc")) // Weak password
                .andExpect(status().isOk()) // Should still return OK but stay on registration page
                .andReturn();
        assertEquals("register", result.getModelAndView().getViewName()); // Registration fails due to weak password
    }
    @Test
    public void testLoginUser_Failure_InvalidCredentials() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                .param("email", "nonexist@gmail.com")
                .param("password", "error"))
                .andExpect(status().isOk()) // Should return OK but stay on login page
                .andReturn();

        assertEquals("login", result.getModelAndView().getViewName()); // Login fails, stays on login page
    }
    @Test
    public void testRegisterUser_Failure_InvalidEmail() throws Exception {
        MvcResult result = mockMvc.perform(post("/register")
                .param("email", "invalid-email") // Invalid email format
                .param("password", "passwords123"))
                .andExpect(status().isOk()) // Should return OK but fail registration
                .andReturn();
        assertEquals("register", result.getModelAndView().getViewName()); // Registration fails due to invalid email
    }
}

package com.bivek.fms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import com.bivek.fms.controller.AuthController;
import com.bivek.fms.model.User;
import com.bivek.fms.service.UserService;

public class AuthControllerUnitTest {
    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks for each test case
    }
    // Successful Unit Test 
    @Test
    public void testShowRegistrationForm_Success() {
        // Simulating showing the registration form. This is expected to succeed.
        String viewName = authController.showRegistrationForm(model);
        // Success: The expected view should be "register"
        assertEquals("register", viewName, "Expected to return 'register' view on success");
    }
    @Test
    public void testRegisterUser_Success() {
        // Mocking successful user registration logic
        User user = new User();
        when(userService.registerUser(user)).thenReturn("Registration successful");
        // Call the controller method and assert the result
        String viewName = authController.registerUser(user, model);
        // Success: Registration should redirect to the "login" view
        assertEquals("login", viewName, "Expected to redirect to 'login' on successful registration");
    }
    @Test
    public void testLoginUser_Success() {
        // Mocking successful user login logic
        User user = new User();
        when(userService.loginUser(user)).thenReturn(true); // Simulate valid credentials
        // Call the controller method and assert the result
        String viewName = authController.loginUser(user, model);
        // Success: Successful login should redirect to home
        assertEquals("redirect:/home", viewName, "Expected to redirect to home on successful login");
    }

//Failure Unit Test Case
    @Test
    public void testRegisterUser_Failure() {
        // Mocking failed user registration logic
        User user = new User();
        when(userService.registerUser(user)).thenReturn("Registration failed"); 
        // Call the controller method and assert the result
        String viewName = authController.registerUser(user, model);
        // Failure: Registration failed, it should stay on the "register" page
        assertEquals("register", viewName, "Expected to stay on 'register' on failed registration");
    }
    @Test
    public void testLoginUser_Failure_InvalidCredentials() {
        // Mocking failed user login logic due to invalid credentials
        User user = new User();
        when(userService.loginUser(user)).thenReturn(false); //invalid credentials
        // Call the controller method and assert the result
        String viewName = authController.loginUser(user, model);
        // Failure: Invalid login should stay on the "login" page
        assertEquals("login", viewName, "Expected to stay on 'login' on invalid credentials");
    }
    @Test
    public void testRegisterUser_Failure_WeakPassword() {
        // Mocking failed user registration due to weak password
        User user = new User();
        user.setPassword("123"); // Weak password
        when(userService.registerUser(user)).thenReturn("Registration failed due to weak password");
        // Call the controller method and assert the result
        String viewName = authController.registerUser(user, model);
        // Failure: Weak password results in staying on the "register" page
        assertEquals("register", viewName, "Expected to stay on 'register' due to weak password");
    }
}

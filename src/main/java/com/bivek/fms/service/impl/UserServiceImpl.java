package com.bivek.fms.service.impl;

import com.bivek.fms.model.User;
import com.bivek.fms.repo.UserRepository;
import com.bivek.fms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String registerUser(User user) {
        // Simple validation check
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null
                || user.getPhoneNumber() == null) {
            return "All fields are required.";
        }

        // Check if username or email already exists in the database
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists. Please choose another one.";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists. Please choose another one.";
        }
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save the user to the database
        userRepository.save(user);
        return "Registration successful! Please login.";
    }

    @Override
    public boolean loginUser(User user) {
        // Find user by username
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            // Compare the stored password with the provided one after encryption
            return passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword());
        }

        return false; // Return false if user does not exist or password does not match
    }
}

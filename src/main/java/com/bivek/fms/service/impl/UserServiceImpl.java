package com.bivek.fms.service.impl;

import com.bivek.fms.entity.User;
import com.bivek.fms.repository.UserRepository;
import com.bivek.fms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String registerUser(User user) {
        // Check if all required fields are present
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null
                || user.getPhoneNumber() == null) {
            return "All fields are required.";
        }
        // Check if username, email, or phone number already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists. Please choose another one.";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists. Please choose another one.";
        }
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return "Phone number already exists. Please choose another one.";
        }
        // Encode the password and save the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Registration successful! Please login.";
    }
    @Override
    public boolean loginUser(User user) {
        // Fetch user by username
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        // If user is found, match the password
        if (existingUser.isPresent()) {
            return passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword());
        }
        return false;
    }
    @Override
    public User findByUsername(String username) {
        // Find and return the user by username, if present
        return userRepository.findByUsername(username).orElse(null);
    }
    @Override
    public User findById(Long userId) {
        // Find and return the user by ID, if present
        return userRepository.findById(userId).orElse(null);
    }
}

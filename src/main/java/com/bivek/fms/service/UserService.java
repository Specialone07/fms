package com.bivek.fms.service;

import com.bivek.fms.entity.User;

public interface UserService {
    String registerUser(User user);

    boolean loginUser(User user);

    User findByUsername(String username);

    User findById(Long userId); 
}

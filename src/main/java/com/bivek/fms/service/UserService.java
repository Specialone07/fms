package com.bivek.fms.service;

import com.bivek.fms.model.User;

public interface UserService {
    String registerUser(User user);

    boolean loginUser(User user);
}

package com.example.task.user.service.service;

import com.example.task.user.service.model.User;

import java.util.List;

public interface UserService {
    User getUserProfile(String jwt);
    List<User> getAllUsers();
    User getUserProfileById(Long id);
}

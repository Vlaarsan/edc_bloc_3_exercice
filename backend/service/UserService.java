package com.example.projectmanager.service;

import com.example.projectmanager.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
}
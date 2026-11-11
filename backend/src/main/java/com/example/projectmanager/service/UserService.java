package com.example.projectmanager.service;

import com.example.projectmanager.dto.UserDTO;
import com.example.projectmanager.dto.UserRequestDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserRequestDTO request);

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> getUserByEmail(String email);

    boolean deleteUser(Long id);
}

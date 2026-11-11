package com.example.projectmanager.controller;

import com.example.projectmanager.dto.UserDTO;
import com.example.projectmanager.dto.UserRequestDTO;
import com.example.projectmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        UserDTO dto = new UserDTO(1L, "alice", "alice@test.com");
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(dto);

        String json = """
            {"username":"alice","email":"alice@test.com","password":"secret"}
            """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    void getAllUsers_sheturnReturnList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                new UserDTO(1L, "alice", "a@test.com"),
                new UserDTO(2L, "bob", "b@test.com")
        ));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("alice"))
                .andExpect(jsonPath("$[1].username").value("bob"));
    }

    @Test
    void getUserById_shouldReturnUserWhenFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(
                java.util.Optional.of(new UserDTO(1L, "alice", "a@test.com")));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"));
    }

    @Test
    void getUserById_shouldReturn404WhenNotFound() throws Exception {
        when(userService.getUserById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_shouldReturn204WhenDeleted() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}

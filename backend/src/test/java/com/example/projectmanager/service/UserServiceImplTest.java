package com.example.projectmanager.service;

import com.example.projectmanager.dto.UserDTO;
import com.example.projectmanager.dto.UserRequestDTO;
import com.example.projectmanager.entity.User;
import com.example.projectmanager.repository.UserRepository;
import com.example.projectmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldSaveAndReturnDto() {
        UserRequestDTO request = new UserRequestDTO("alice", "alice@test.com", "password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("alice");
        savedUser.setEmail("alice@test.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("alice");
        assertThat(result.getEmail()).isEqualTo("alice@test.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnList() {
        User u1 = new User(); u1.setId(1L); u1.setUsername("alice"); u1.setEmail("a@test.com");
        User u2 = new User(); u2.setId(2L); u2.setUsername("bob"); u2.setEmail("b@test.com");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("alice");
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnDtoWhenFound() {
        User u = new User(); u.setId(1L); u.setUsername("alice"); u.setEmail("a@test.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("alice");
    }

    @Test
    void getUserById_shouldReturnEmptyWhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.getUserById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteUser_shouldReturnTrueWhenDeleted() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.deleteUser(1L);

        assertThat(result).isTrue();
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldReturnFalseWhenNotFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        boolean result = userService.deleteUser(99L);

        assertThat(result).isFalse();
        verify(userRepository, never()).deleteById(99L);
    }
}

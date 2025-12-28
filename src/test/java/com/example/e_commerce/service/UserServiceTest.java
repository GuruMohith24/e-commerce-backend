package com.example.e_commerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.e_commerce.dto.UserRequest;
import com.example.e_commerce.dto.UserResponse;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldEncryptPassword_AndReturnResponse() {
        // Arrange
        UserRequest request = new UserRequest("John Doe", "john@example.com", "password123");
        User savedUser = new User(1L, "John Doe", "john@example.com", "encodedPassword");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(passwordEncoder).encode("password123"); // Verify encryption happened
        verify(userRepository).save(any(User.class));
    }
}

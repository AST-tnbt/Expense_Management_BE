package com.example.auth_service;

import com.example.auth_service.controllers.TokenController;
import com.example.auth_service.controllers.UserController;
import com.example.auth_service.dtos.ChangePassDto;
import com.example.auth_service.dtos.UpdateUserRequest;
import com.example.auth_service.dtos.UserResponse;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.AuthenticationService;
import com.example.auth_service.services.JwtService;
import com.example.auth_service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void updateUserTest() {
        User mockUser = new User();
        UUID userId = UUID.randomUUID();
        mockUser.setId(userId);
        mockUser.setEmail("tin@gmail.com");
        mockUser.setBirthDay(LocalDate.parse("2004-03-27"));
        mockUser.setGender("Nam");
        mockUser.setFullName("Tin dep trai");
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setBirthDay(LocalDate.parse("2004-03-27"));
        updateUserRequest.setGender("Nam");
        updateUserRequest.setFullName("Tin dep trai");
        when(userService.updateUser(userId,updateUserRequest)).thenReturn(mockUser);
        ResponseEntity<User> response = userController.updateUser(userId, updateUserRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("tin@gmail.com", response.getBody().getUsername());
        assertEquals(LocalDate.parse("2004-03-27"), response.getBody().getBirthDay());
        assertEquals("Nam", response.getBody().getGender());
        assertEquals("Tin dep trai", response.getBody().getFullName());
    }
    @Test
    public void changePasswordTest() {
        UUID userId = UUID.randomUUID();
        ChangePassDto changePassDto = new ChangePassDto();
        changePassDto.setCurrentPassword("123");
        changePassDto.setNewPassword("1234");
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setPassword("123");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        ResponseEntity<?> response = userController.changePassword(changePassDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).changePassword(userId, changePassDto);
    }
    @Test
    public void getPasswordLastChangedTime_shouldReturnTimestamp_whenUserExists() {
        UUID userId = UUID.randomUUID();
        LocalDateTime passwordChangedTime = LocalDateTime.now();

        User user = new User();
        user.setPasswordLastChanged(passwordChangedTime);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<LocalDateTime> response = userController.getPasswordLastChangedTime(userId.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passwordChangedTime, response.getBody());
    }

    @Test
    void getPasswordLastChangedTime_shouldReturnNotFound_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<LocalDateTime> response = userController.getPasswordLastChangedTime(userId.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

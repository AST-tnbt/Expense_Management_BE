package com.example.auth_service;

import com.example.auth_service.controllers.AuthenticationController;
import com.example.auth_service.dtos.LoginResponse;
import com.example.auth_service.dtos.LoginUserDto;
import com.example.auth_service.dtos.RegisterUserDto;
import com.example.auth_service.entities.User;
import com.example.auth_service.services.AuthenticationService;
import com.example.auth_service.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private AuthenticationController authenticationController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void RegisterTest() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("tin@gmail.com");
        registerUserDto.setBirthDay(LocalDate.parse("2004-02-27"));
        registerUserDto.setGender("Nam");
        registerUserDto.setFullName("Tin");
        registerUserDto.setPassword("123");
        User mockUser = new User();
        mockUser.setEmail("tin@gmail.com");
        mockUser.setBirthDay(LocalDate.parse("2004-02-27"));
        mockUser.setGender("Nam");
        mockUser.setFullName("Tin");
        when(authenticationService.signup(registerUserDto)).thenReturn(mockUser);
        ResponseEntity<User> response = authenticationController.register(registerUserDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("tin@gmail.com", response.getBody().getUsername());
        assertEquals(LocalDate.parse("2004-02-27"), response.getBody().getBirthDay());
        assertEquals("Nam", response.getBody().getGender());
        assertEquals("Tin", response.getBody().getFullName());
    }
    @Test
    public void LoginTest() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("tin@gmail.com");
        loginUserDto.setPassword("123");
        User mockUser = new User();
        mockUser.setEmail("tin@gmail.com");
        when(authenticationService.authenticate(loginUserDto)).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(mockUser)).thenReturn("refreshToken");
        when(jwtService.getExpirationTime()).thenReturn(3600000L);
        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginUserDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("accessToken", response.getBody().getAccessToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
        assertEquals("refreshToken", response.getBody().getRefreshToken());
        assertEquals(3600000, response.getBody().getExpiresIn());
    }
}

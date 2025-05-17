package com.example.auth_service;

import com.example.auth_service.controllers.TokenController;
import com.example.auth_service.dtos.LoginResponse;
import com.example.auth_service.dtos.LogoutDto;
import com.example.auth_service.dtos.MessageResponse;
import com.example.auth_service.entities.User;
import com.example.auth_service.services.AuthenticationService;
import com.example.auth_service.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenControllerTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private TokenController tokenController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }
    @Test
    public void LogoutTest() {
        String accessToken = "Bearer accessToken";
        LogoutDto logoutDto = new LogoutDto();
        logoutDto.setRefreshToken("refreshToken");
        ResponseEntity<MessageResponse> response = tokenController.logout(accessToken, logoutDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Logout successfully", response.getBody().getMessage());
        Mockito.verify(jwtService).addToBlacklist("accessToken");
        Mockito.verify(jwtService).addToBlacklist("refreshToken");
    }
    @Test
    public void RefreshTest() {
        String token = "Bearer refreshToken";
        User mockUser = new User();
        mockUser.setEmail("tindeptrai@gmail.com");
        when(jwtService.extractUsername("refreshToken")).thenReturn("tindeptrai@gmail.com");
        when(authenticationService.loadUserByUsername("tindeptrai@gmail.com")).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn("new-access-token");
        when(jwtService.generateRefreshToken(mockUser)).thenReturn("new-refresh-token");
        when(jwtService.getExpirationTime()).thenReturn(3600000L);

        ResponseEntity<LoginResponse> response = tokenController.refresh(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("new-access-token", response.getBody().getAccessToken());
        assertEquals("new-refresh-token", response.getBody().getRefreshToken());
        assertEquals(3600000, response.getBody().getExpiresIn());
        verify(jwtService).addToBlacklist("refreshToken");
    }
}

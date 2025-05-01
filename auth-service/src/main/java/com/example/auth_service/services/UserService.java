package com.example.auth_service.services;

import com.example.auth_service.dtos.UpdateUserRequest;
import com.example.auth_service.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> allUsers();
    User updateUser(UUID id, UpdateUserRequest request);
}

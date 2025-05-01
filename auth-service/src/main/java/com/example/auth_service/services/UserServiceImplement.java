package com.example.auth_service.services;

import com.example.auth_service.dtos.UpdateUserRequest;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User updateUser(UUID id, UpdateUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();
        user.setFullName(request.getFullName());
        user.setBirthDay(request.getBirthDay());
        user.setGender(request.getGender());

        return userRepository.save(user);
    }
}

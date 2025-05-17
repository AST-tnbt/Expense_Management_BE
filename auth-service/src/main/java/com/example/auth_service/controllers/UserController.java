package com.example.auth_service.controllers;

import com.example.auth_service.dtos.ChangePassDto;
import com.example.auth_service.dtos.UpdateUserRequest;
import com.example.auth_service.dtos.UserInfoResponseDto;
import com.example.auth_service.dtos.UserResponse;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.services.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponseDto> authenticatedUser(@AuthenticationPrincipal User user) {
        UserInfoResponseDto currentUser = getUserInfo(user);
        return ResponseEntity.ok(currentUser);
    }

    @Cacheable(value = "authenticatedUsers", key = "#user.id", unless = "#result == null")
    private UserInfoResponseDto getUserInfo(User user) {
        return new UserInfoResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.getBirthDay(), user.getGender());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        userService.changePassword(currentUser.getId(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/password-last-changed")
    public ResponseEntity<LocalDateTime> getPasswordLastChangedTime(@PathVariable String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(user -> ResponseEntity.ok(user.getPasswordLastChanged()))
                .orElse(ResponseEntity.notFound().build());
    }

}

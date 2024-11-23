package com.firozkhan.server.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.Response.UserResponseDTO;
import com.firozkhan.server.enums.Role;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.DataFetcher;
import com.firozkhan.server.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DataFetcher dataFetcher;

    public UserController(UserService userService, DataFetcher dataFetcher) {
        this.userService = userService;
        this.dataFetcher = dataFetcher;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUser() {
        return userService.getAll();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(HttpServletRequest request) {

        User user = dataFetcher.getCurrentUser(request);

        return ResponseEntity
                .ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    public User createUser(@Argument String username, @Argument String email, @Argument String password,
            @Argument String role) {
        Role userRole = Role.valueOf(role.toUpperCase());
        return userService.create(new User.Builder()
                .username(username)
                .email(email)
                .password(password)
                .role(userRole)
                .build());
    }
}

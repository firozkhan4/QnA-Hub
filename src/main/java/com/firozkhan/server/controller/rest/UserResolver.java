package com.firozkhan.server.controller.rest;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.UserResponseDTO;
import com.firozkhan.server.enums.Role;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.DataFetcher;
import com.firozkhan.server.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserResolver {

    private final UserService userService;
    private final DataFetcher dataFetcher;

    public UserResolver(UserService userService, DataFetcher dataFetcher) {
        this.userService = userService;
        this.dataFetcher = dataFetcher;
    }

    public List<User> getAllUser() {
        return userService.getAll();
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getCurrentUser(HttpServletRequest request) {

        User user = dataFetcher.getCurrentUser(request);

        return ResponseEntity
                .ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()));
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

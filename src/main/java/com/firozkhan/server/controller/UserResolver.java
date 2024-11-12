package com.firozkhan.server.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.firozkhan.server.enums.Role;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.UserService;

@Controller
public class UserResolver {

    private final UserService userService;

    public UserResolver(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @QueryMapping
    public ResponseEntity<User> getUserById(@Argument String id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @MutationMapping
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

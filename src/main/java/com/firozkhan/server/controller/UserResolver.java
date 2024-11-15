package com.firozkhan.server.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.enums.Role;
import com.firozkhan.server.error.InvalidCredentialsException;
import com.firozkhan.server.jwt.JwtService;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserResolver {

    private final UserService userService;
    private final JwtService jwtService;

    public UserResolver(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<User> getUserById(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        String jwtToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        String username = null;

        if (jwtToken != null) {
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (Exception e) {
                log.error("Invalid JWT Token: {}", e.getMessage());
                throw new InvalidCredentialsException("Invalid Token");
            }
        }

        log.info("GetUserById");

        return userService.getByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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

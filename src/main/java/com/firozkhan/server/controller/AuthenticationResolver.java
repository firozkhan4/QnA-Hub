package com.firozkhan.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.model.User;
import com.firozkhan.server.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticationResolver {

    private final AuthenticationService authenticationService;

    public AuthenticationResolver(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request, HttpServletResponse response) {

        log.info("Request in register controller");

        String token = authenticationService.register(
                request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60 * 60);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("OK");

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request, HttpServletResponse response) {

        String token = authenticationService.register(
                request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());

        Cookie jwtCookie = new Cookie("token", token);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60 * 60);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("OK");
    }

}

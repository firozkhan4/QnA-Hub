package com.firozkhan.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.AuthResponseDTO;
import com.firozkhan.server.dto.UserResponseDTO;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.AuthenticationService;
import com.firozkhan.server.utils.ValidationUtils;

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
    public ResponseEntity<UserResponseDTO> register(@RequestBody User request, HttpServletResponse response) {

        log.info("New User is Created");

        AuthResponseDTO authResponseDTO = authenticationService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRole());

        Cookie jwtCookie = new Cookie("token", authResponseDTO.toString());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(authResponseDTO.getUser());

    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody User request, HttpServletResponse response) {

        log.info("User Login");

        if (ValidationUtils.isNullOrEmpty(request.getUsername())
                || ValidationUtils.isNullOrEmpty(request.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        AuthResponseDTO authResponseDTO = authenticationService.authenticate(request);

        Cookie jwtCookie = new Cookie("token", authResponseDTO.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(authResponseDTO.getUser());
    }

}

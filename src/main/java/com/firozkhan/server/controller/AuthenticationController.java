package com.firozkhan.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firozkhan.server.dto.Response.AuthResponseDTO;
import com.firozkhan.server.dto.Response.UserResponseDTO;
import com.firozkhan.server.model.User;
import com.firozkhan.server.service.AuthenticationService;
import com.firozkhan.server.utils.ValidationUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final int CookieExpiry = 2592000;

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User request, HttpServletResponse response) {

        log.info("New User is Created");

        AuthResponseDTO authResponseDTO = authenticationService.register(request);

        setCookie(response, authResponseDTO.getToken(), "token", CookieExpiry);

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

        setCookie(response, authResponseDTO.getToken(), "token", CookieExpiry);

        return ResponseEntity.ok(authResponseDTO.getUser());
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletResponse response) {

        log.info("User Logouts");

        setCookie(response, null, "token", 0);
        return ResponseEntity.ok(true);
    }

    private void setCookie(HttpServletResponse response, String token, String cookieName, int maxAge) {
        Cookie jwtCookie = new Cookie(cookieName, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(maxAge);

        response.addCookie(jwtCookie);
    }

}

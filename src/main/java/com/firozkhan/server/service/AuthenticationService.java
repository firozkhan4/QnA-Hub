package com.firozkhan.server.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firozkhan.server.dto.AuthResponseDTO;
import com.firozkhan.server.dto.UserResponseDTO;
import com.firozkhan.server.enums.Role;
import com.firozkhan.server.error.InvalidCredentialsException;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.jwt.JwtService;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(String username, String email, String password, Role role) {

        if (!userRepository.findByUsernameOrEmail(username, email).isEmpty()) {
            throw new InvalidCredentialsException("Username or Password Already Exists");
        }

        User user = new User.Builder()
                .username(username)
                .email(email)
                .role(role)
                .password(passwordEncoder.encode(password))
                .build();

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()),
                token);

    }

    public AuthResponseDTO authenticate(User request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new NotFoundException("User not found by id"));

        log.info(user.toString());

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()), token);
    }
}

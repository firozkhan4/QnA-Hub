package com.firozkhan.server.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.firozkhan.server.dto.Response.AuthResponseDTO;
import com.firozkhan.server.dto.Response.UserResponseDTO;
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

    public AuthResponseDTO register(User user) {

        if (!userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isEmpty()) {
            throw new InvalidCredentialsException("Username or Password Already Exists");
        }

        User newUser = new User.Builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        newUser = userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);

        return new AuthResponseDTO(
                new UserResponseDTO(
                        newUser.getId(),
                        newUser.getUsername(),
                        newUser.getEmail(),
                        newUser.getRole()),
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

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()),
                token);
    }
}

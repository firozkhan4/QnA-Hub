package com.firozkhan.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.firozkhan.server.error.InvalidCredentialsException;
import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.jwt.JwtService;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class DataFetcher {

    private JwtService jwtService;
    private UserRepository userRepository;

    public DataFetcher(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    private static Logger logger = LoggerFactory.getLogger("DataFetcher");

    public User getCurrentUser(HttpServletRequest request) {
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
                logger.error("Invalid JWT Token: {}", e.getMessage());
                throw new InvalidCredentialsException("Invalid Token");
            }
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User Not Found "));
    }
}

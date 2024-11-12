package com.firozkhan.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.firozkhan.server.error.NotFoundException;
import com.firozkhan.server.model.User;
import com.firozkhan.server.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    }

    public User create(User entity) {
        Boolean isUserPresent = userRepository
                .findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
                .isEmpty();

        if (!isUserPresent) {
            throw new RuntimeException("User Already Exists By Username and email");
        }

        return userRepository.save(entity);
    }

    public User updateUsername(String id, String username) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found By id: " + id));

        Boolean isUserPresent = userRepository.findByUsername(username).isEmpty();

        if (!isUserPresent) {
            throw new RuntimeException("Username is not available");
        }

        return userRepository.save(existUser
                .toBuilder()
                .username(username)
                .build());
    }
}

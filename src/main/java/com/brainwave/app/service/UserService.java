package com.brainwave.app.service;

import com.brainwave.app.model.User;
import com.brainwave.app.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getUsername().equalsIgnoreCase("admin")) {
            user.setRole("ROLE_ADMIN");
        } else if (user.getUsername().equalsIgnoreCase("teacher")) {
            user.setRole("ROLE_TEACHER");
        } else {
            user.setRole("ROLE_STUDENT");
        }

        userRepository.save(user);
    }
}
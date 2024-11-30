package com.openclassrooms.rental_backend.service;

import com.openclassrooms.rental_backend.entity.User;

import com.openclassrooms.rental_backend.exception.UserNotFoundException;
import com.openclassrooms.rental_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}

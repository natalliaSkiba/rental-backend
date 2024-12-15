package com.openclassrooms.rental_backend.service;

import com.openclassrooms.rental_backend.DTO.UserResponseDTO;
import com.openclassrooms.rental_backend.entity.User;

import com.openclassrooms.rental_backend.exception.UserNotFoundException;
import com.openclassrooms.rental_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final  AuthService authService;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findByIdOrThrow(id);
        return authService.toUserResponseDTO(user);
    }

}

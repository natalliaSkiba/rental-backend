package com.openclassrooms.rental_backend.service;

import com.openclassrooms.rental_backend.DTO.LoginRequest;
import com.openclassrooms.rental_backend.DTO.RegisterRequest;
import com.openclassrooms.rental_backend.DTO.UserResponse;
import com.openclassrooms.rental_backend.config.JwtUtil;
import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.exception.UserAlreadyExistsException;
import com.openclassrooms.rental_backend.exception.UserNotFoundException;
import com.openclassrooms.rental_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email " + request.getEmail() + " is already in use");
        }
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    public UserResponse getUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        return response;
    }

    public String login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        return jwtUtil.generateToken(authentication);
    }

    public UserResponse getCurrentUser(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String email = jwtUtil.extractUsername(jwt);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return getUserResponse(user);
    }
}

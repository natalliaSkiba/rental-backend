package com.openclassrooms.rental_backend.controller;

import com.openclassrooms.rental_backend.DTO.MessageRequest;
import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.service.MessageService;
import com.openclassrooms.rental_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new message", description = "Creates a message associated with a user and rental")
    public ResponseEntity<String> createMessage(
            @RequestBody MessageRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String email = jwt.getClaimAsString("sub");
        User user = userService.findByEmail(email);
        validateRequest(request, user);
        messageService.createMessage(request.getMessage(), user.getId(), request.getRentalId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Message created successfully");
    }

    private void validateRequest(MessageRequest request, User user) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content must not be empty");
        }
        if (request.getMessage().length() > 2000) {
            throw new IllegalArgumentException("Message content exceeds the maximum length of 2000 characters");
        }
        if (request.getRentalId() == null) {
            throw new IllegalArgumentException("Rental ID must not be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

    }
}

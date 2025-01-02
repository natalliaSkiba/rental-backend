package com.openclassrooms.rental_backend.controller;

import com.openclassrooms.rental_backend.DTO.MessageRequest;
import com.openclassrooms.rental_backend.service.MessageService;
import com.openclassrooms.rental_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new message", description = "Creates a message associated with a user and rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent with success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"message\": \"Message send with success\" }"))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{}"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Token is missing or invalid",
                    content = @Content)
    })
    public ResponseEntity<?> createMessage(
            @RequestBody MessageRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("sub");
        messageService.createMessage(request.getMessage(), request.getUserId(), request.getRentalId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");
        return ResponseEntity.ok(response);
    }
}

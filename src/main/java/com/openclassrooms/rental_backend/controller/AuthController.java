package com.openclassrooms.rental_backend.controller;

import com.nimbusds.oauth2.sdk.TokenResponse;
import com.openclassrooms.rental_backend.DTO.LoginRequest;
import com.openclassrooms.rental_backend.DTO.RegisterRequest;
import com.openclassrooms.rental_backend.DTO.UserResponseDTO;
import com.openclassrooms.rental_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "User registration", description = "Registers a new user in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered, JWT token returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request. Email is already in use or input is invalid",
                    content = @Content(mediaType = "application/json")),

    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        authService.register(request);

        String token = authService.login(new LoginRequest(request.getEmail(), request.getPassword()));

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "User login", description = "Authenticates a user and provides a JWT token")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful, JWT token returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"token\": \"jwt\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"message\": \"error\" }")
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get current user info", description = "Returns the current authenticated user's information")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User information retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Token is missing or invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ }")
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String token, HttpServletRequest request) {

        UserResponseDTO userResponse = authService.getCurrentUser(token);
        return ResponseEntity.ok(userResponse);
    }
}


package com.openclassrooms.rental_backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleUnauthorized(InvalidTokenException ex, HttpServletRequest request) {

        if (request.getRequestURI().startsWith("/api/auth")) {
            // Empty JSON for AuthController
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");
        }
        // For other - empty body
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleInternalError(Exception ex) {
        //Return a 500 error
        Map<String, String> response = new HashMap<>();
        response.put("message", "Server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Map<String, String>> handleBadCredentialsForAutn(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "error"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        // Return an empty JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        // Return an empty JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<>());
    }
    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<?> handRentalNotFound(RentalNotFoundException ex) {
        // Empty body
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Void> handleSecurityException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

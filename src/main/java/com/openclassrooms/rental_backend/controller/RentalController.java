package com.openclassrooms.rental_backend.controller;

import com.openclassrooms.rental_backend.DTO.RentalDTO;
import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.service.RentalService;
import com.openclassrooms.rental_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    @Operation(summary = "Get all rentals", description = "Retrieve a list of all rental properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Token is missing or invalid",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<Map<String, List<RentalDTO>>> getAllRentals() {
        List<RentalDTO> rentals = rentalService.getAllRentals();
        Map<String, List<RentalDTO>> response = Map.of("rentals", rentals);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get rental by ID", description = "Retrieve a rental property by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved rental",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Token is missing or invalid",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {
        RentalDTO rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @Operation(summary = "Create a rental property", description = "Create a new rental property")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rental property created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"message\": \"Rental created!\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Token is missing or invalid",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<?> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) throws IOException {

        String email = jwt.getClaimAsString("sub");
        User owner = userService.findByEmail(email);

        String picturePath = file != null ? rentalService.saveImage(file) : null;

        rentalService.createRental(name, surface, price, description, owner.getId(), picturePath);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a rental property", description = "Update an existing rental property")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rental property updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{ \"message\": \"Rental updated!\" }")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Token is missing or invalid, You are not the owner of this rental",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile file,
            @AuthenticationPrincipal Jwt jwt) throws IOException {

        String email = jwt.getClaimAsString("sub");
        User owner = userService.findByEmail(email);

        String picturePath = file != null ? rentalService.saveImage(file) : null;

        rentalService.updateRental(id, name, surface, price, description, owner.getId(), picturePath);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        return ResponseEntity.ok(response);
    }
}

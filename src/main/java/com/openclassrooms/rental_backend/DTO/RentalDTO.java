package com.openclassrooms.rental_backend.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Integer id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

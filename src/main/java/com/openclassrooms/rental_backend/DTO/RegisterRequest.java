package com.openclassrooms.rental_backend.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
}

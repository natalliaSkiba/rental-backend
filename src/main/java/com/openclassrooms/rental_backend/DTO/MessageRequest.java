package com.openclassrooms.rental_backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageRequest {
    @JsonProperty("message")
    private String message;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("rental_id")
    private Integer rentalId;
}

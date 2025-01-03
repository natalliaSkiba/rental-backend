package com.openclassrooms.rental_backend.service;

import com.openclassrooms.rental_backend.entity.Message;
import com.openclassrooms.rental_backend.entity.Rental;
import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.repository.MessageRepository;
import com.openclassrooms.rental_backend.repository.RentalRepository;
import com.openclassrooms.rental_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public void createMessage(String messageContent, Integer userId, Integer rentalId) {
        validateMessageContent(messageContent);

        // Using repository methods to find entities or throw exceptions
        Rental rental = rentalRepository.findByIdOrThrow(rentalId);
        User user = userRepository.findByIdOrThrow(userId);

        Message message = new Message();
        message.setRental(rental);
        message.setUser(user);
        message.setContent(messageContent);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messageRepository.save(message);
    }

    private void validateMessageContent(String messageContent) {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content must not be empty");
        }
        if (messageContent.length() > 2000) {
            throw new IllegalArgumentException("Message content exceeds the maximum length of 2000 characters");
        }
    }


}

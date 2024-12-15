package com.openclassrooms.rental_backend.service;

import com.openclassrooms.rental_backend.DTO.RentalDTO;
import com.openclassrooms.rental_backend.entity.Rental;
import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.exception.RentalNotFoundException;
import com.openclassrooms.rental_backend.repository.RentalRepository;
import com.openclassrooms.rental_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RentalService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.base-url}")
    private String baseUrl;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;

    public String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return baseUrl + fileName;
    }

    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RentalDTO getRentalById(Integer id) {
        return rentalRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));
    }

    public void createRental(String name, Double surface, Double price, String description, Integer ownerId, String picturePath) {
        User owner = userRepository.findByIdOrThrow(ownerId);
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setOwner(owner);
        rental.setPicture(picturePath);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        rentalRepository.save(rental);
    }

    public void updateRental(Integer id, String name, Double surface, Double price, String description, Integer ownerId, String picturePath) {

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException("Rental not found"));

        if (!rental.getOwner().getId().equals(ownerId)) {
            log.info(""+ rental.getOwner().getId());
            log.info("ownerID " +ownerId);
            throw new SecurityException("You are not the owner of this rental");
        }
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        if (picturePath != null) {
            rental.setPicture(picturePath);
        }
        rental.setUpdatedAt(LocalDateTime.now());

        rentalRepository.save(rental);
    }

    private RentalDTO convertToDTO(Rental rental) {
        RentalDTO dto = new RentalDTO();
        dto.setId(rental.getId());
        dto.setName(rental.getName());
        dto.setSurface(rental.getSurface());
        dto.setPrice(rental.getPrice());
        dto.setPicture(rental.getPicture());
        dto.setDescription(rental.getDescription());
        dto.setOwnerID(rental.getOwner().getId());
        dto.setCreatedAt(rental.getCreatedAt());
        dto.setUpdatedAt(rental.getUpdatedAt());
        return dto;
    }
}

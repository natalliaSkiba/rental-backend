package com.openclassrooms.rental_backend.repository;

import com.openclassrooms.rental_backend.entity.Rental;
import com.openclassrooms.rental_backend.exception.RentalNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    /**
     * Finds a rental by ID or throws an exception if not found.
     * Simplifies handling when the rental must exist.
     */
    default Rental findByIdOrThrow(Integer rentalId) {
        return findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found with ID: " + rentalId));
    }
}

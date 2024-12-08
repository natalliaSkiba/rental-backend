package com.openclassrooms.rental_backend.repository;

import com.openclassrooms.rental_backend.entity.User;
import com.openclassrooms.rental_backend.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Finds a user by email.
     * Useful for retrieving user details or validating existence.
     */
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    /**
     * Finds a user by ID or throws an exception if not found.
     * Simplifies handling when the user must exist.
     */
    default User findByIdOrThrow(Integer userId) {
        return findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}

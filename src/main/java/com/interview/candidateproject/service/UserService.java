package com.interview.candidateproject.service;

import com.interview.candidateproject.entity.User;
import com.interview.candidateproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        // Basic validation
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setEmail(userDetails.getEmail());
                    user.setActive(userDetails.getActive());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #1
     * 
     * This method should search for users by name (first name or last name).
     * It should be case-insensitive and support partial matches.
     * 
     * Hint: Reuse all the exiting functions
     * 
     * @param name The name to search for
     * @return List of users matching the search criteria
     */
    public List<User> searchUsersByName(String name) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #2
     * 
     * This method should validate user data before creating a new user.
     * Validation rules:
     * - Username must be at least 3 characters long
     * - Email must be valid format
     * - First name and last name must not be empty
     * - Username and email must be unique
     * 
     * @param user The user to validate
     * @return true if valid, false otherwise
     */
    public boolean validateUser(User user) {
        // TODO: Implement comprehensive user validation
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #10
     * 
     * This method should deactivate a user account and perform cleanup.
     * 
     * Requirements:
     * - Find the user by ID (throw RuntimeException with message "User not found"
     * if not found)
     * - Check if user is already inactive (throw IllegalStateException with message
     * "User is already inactive")
     * - Set the user's active status to false
     * - Save and return the updated user
     * 
     * @param userId The ID of the user to deactivate
     * @return The deactivated user
     * @throws RuntimeException      if user not found
     * @throws IllegalStateException if user is already inactive
     */
    public User deactivateUser(Long userId) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #11
     * 
     * This method should update a user's email with validation.
     * 
     * Requirements:
     * - Find the user by ID (throw RuntimeException with message "User not found"
     * if not found)
     * - Validate that the new email is not null or empty (throw
     * IllegalArgumentException)
     * - Validate that the new email is in valid format using regex (throw
     * IllegalArgumentException)
     * - Check if another user already has this email (throw
     * IllegalArgumentException with message "Email already in use")
     * - Update the email and save the user
     * - Return the updated user
     * 
     * Hint: Email regex pattern: ^[A-Za-z0-9+_.-]+@(.+)$
     * 
     * @param userId   The user ID
     * @param newEmail The new email address
     * @return The updated user
     * @throws RuntimeException         if user not found
     * @throws IllegalArgumentException if email is invalid or already in use
     */
    public User updateUserEmail(Long userId, String newEmail) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }
}

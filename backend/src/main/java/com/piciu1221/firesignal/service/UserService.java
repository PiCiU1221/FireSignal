package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.controller.UserController;
import com.piciu1221.firesignal.dto.UserDTO;
import com.piciu1221.firesignal.dto.UserRoleResponse;
import com.piciu1221.firesignal.exceptions.UserNotFoundException;
import com.piciu1221.firesignal.exceptions.UserRoleUpdateException;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.entity.User;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.repository.UserRepository;
import com.piciu1221.firesignal.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for user-related operations, such as login, registration, and role retrieval.
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final FirefighterRepository firefighterRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, FirefighterRepository firefighterRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.firefighterRepository = firefighterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Process user login based on the provided UserDTO.
     *
     * @param userDTO The UserDTO containing login credentials.
     * @return ApiResponse with the login result message.
     */
    public ApiResponse<String> processLogin(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        // Log the login attempt
        logger.info("Attempting login for user '{}'", username);

        // Find the user by the provided username
        User user = userRepository.findByUsername(username);

        // Check if the user is found
        if (user == null) {
            // Log the user not found
            logger.warn("User '{}' not found during login attempt", username);
            return new ApiResponse<>(false, "User not found", null);
        }

        // Check if the provided password matches the user's password
        if (!user.getPassword().equals(password)) {
            // Log incorrect password attempt
            logger.warn("Incorrect password for user '{}'", username);
            return new ApiResponse<>(false, "Incorrect password", null);
        }

        // Login successful
        logger.info("Login successful for user '{}'", username);
        return new ApiResponse<>(true, "Login successful", "Additional data if needed");
    }

    /**
     * Register a new user based on the provided UserDTO.
     *
     * @param userDTO The UserDTO containing registration details.
     * @return ApiResponse with the registration result message.
     */
    public ApiResponse<String> registerUser(UserDTO userDTO) {
        try {
            // Extract username and password from the UserDTO
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();

            // Log the registration attempt
            logger.info("Attempting to register user '{}'", username);

            // Check if a user with the same username already exists
            if (checkIfUserExists(username)) {
                // User already exists, return an error response
                logger.warn("Username '{}' is already taken during registration attempt", username);
                return ApiResponse.error("Username '" + username + "' is already taken.");
            }

            // Create a new User object with the provided username and password
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));

            // Use Optional for a more nuanced handling of the repository result
            Optional<User> savedUser = Optional.of(userRepository.save(newUser));

            // Log successful registration
            logger.info("User '{}' registered successfully.", newUser.getUsername());

            // Return a success response
            return ApiResponse.success("Registration successful for user '" + username + "'.");
        } catch (Exception e) {
            // Catch any unexpected exceptions and return an error response
            logger.error("Failed to register the user.", e);
            return ApiResponse.error("Failed to register the user. " + e.getMessage());
        }
    }

    /**
     * Retrieves the firefighter name associated with the given username.
     *
     * @param username The username for which to retrieve the firefighter name.
     * @return String representing the firefighter name or null if not found.
     */
    public String getFirefighterName(String username) {
        Firefighter firefighter = firefighterRepository.findByFirefighterUsername(username);

        if (firefighter != null) {
            return firefighter.getFirefighterName();
        }

        return null; // Return null if no firefighter is found for the given username
    }

    /**
     * Retrieves the role of the user with the given username.
     *
     * @param username The username for which to retrieve the user role.
     * @return UserRoleResponse containing the user role or null if the user is not found.
     */
    public UserRoleResponse getUserRole(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            String userRole = user.getRole();
            return new UserRoleResponse(userRole);
        } else {
            // Handle the case where the user is not found
            return new UserRoleResponse(null);
        }
    }

    /**
     * Set the role of the user with the given username.
     *
     * @param username The username for which to set the user role.
     * @param role     The new role to set.
     * @throws UserNotFoundException     if the user with the given username is not found.
     * @throws UserRoleUpdateException if there is an error updating the user role.
     */
    public void setUserRole(String username, String role) throws UserNotFoundException, UserRoleUpdateException {
        try {
            // Find the user by the provided username
            User user = userRepository.findByUsername(username);

            // Check if the user is found
            if (user == null) {
                // User not found, throw a UserNotFoundException
                throw new UserNotFoundException("User '" + username + "' not found when setting role");
            }

            // Update the user's role
            user.setRole(role);
            userRepository.save(user);

            // Log successful role update
            logger.info("Role '{}' set successfully for user '{}'", role, username);
        } catch (Exception e) {
            // Catch any unexpected exceptions and log the error
            logger.error("Failed to set role for user '{}'", username, e);

            // Throw a UserRoleUpdateException with the original exception as the cause
            throw new UserRoleUpdateException("Error setting role for user: " + username, e);
        }
    }

    public boolean checkIfUserExists(String username) {
        return userRepository.existsByUsername(username);
    }
}

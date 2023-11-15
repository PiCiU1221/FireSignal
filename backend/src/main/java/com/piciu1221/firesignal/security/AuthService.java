package com.piciu1221.firesignal.security;

import com.piciu1221.firesignal.controller.UserController;
import com.piciu1221.firesignal.dto.UserDTO;
import com.piciu1221.firesignal.model.User;
import com.piciu1221.firesignal.repository.UserRepository;
import com.piciu1221.firesignal.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtUtils jwtUtils,
            UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public ApiResponse<String> authenticate(UserDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();

        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Load user details
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                // Generate JWT token
                String jwtToken = jwtUtils.generateToken(userDetails);

                // Log authentication success
                logger.info("Authentication successful for user '{}'", username);

                // Return success response
                return ApiResponse.success("Authentication successful", jwtToken);
            } else {
                // Log user details not available
                logger.warn("User details not available after authentication for user '{}'", username);

                // Return error response
                return ApiResponse.error("User details not available after authentication");
            }
        } catch (BadCredentialsException e) {
            // Log authentication failure due to bad credentials
            logger.warn("Authentication failed for user '{}' due to bad credentials", username);

            // Check if the user exists
            User user = userRepository.findByUsername(username);

            // Return error response based on whether the username exists or not
            if (user != null) {
                // Username exists, but password is incorrect
                return ApiResponse.error("Incorrect password");
            } else {
                // Username doesn't exist
                return ApiResponse.error("Username not found");
            }
        } catch (Exception e) {
            // Log other authentication failures
            logger.error("Authentication failed for user '{}': {}", username, e.getMessage(), e);

            // Return error response
            return ApiResponse.error("Authentication failed. Please try again later.");
        }
    }
}

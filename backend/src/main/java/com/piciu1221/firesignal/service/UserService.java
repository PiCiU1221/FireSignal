package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.dto.LoginDTO;
import com.piciu1221.firesignal.dto.LoginResponseDTO;
import com.piciu1221.firesignal.dto.UserRoleResponse;
import com.piciu1221.firesignal.model.Firefighter;
import com.piciu1221.firesignal.model.User;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FirefighterRepository firefighterRepository;

    @Autowired
    public UserService(UserRepository userRepository, FirefighterRepository firefighterRepository) {
        this.userRepository = userRepository;
        this.firefighterRepository = firefighterRepository;
    }

    public ResponseEntity<String> processLogin(LoginDTO loginRequest) {
        LoginResponseDTO loginResponse = loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (loginResponse.isSuccess()) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    public LoginResponseDTO loginUser(String username, String password) {
        // Find the user by the provided username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // User not found, return failure response
            return new LoginResponseDTO(false, "User not found");
        }

        if (!user.getPassword().equals(password)) {
            // Incorrect password, return failure response
            return new LoginResponseDTO(false, "Incorrect password");
        }

        // Login successful, return success response
        return new LoginResponseDTO(true, "Login successful");
    }

    public String registerUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            return "Username already exists";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.save(newUser);

        return "User registered successfully";
    }

    public String getFirefighterName(String username) {
        Firefighter firefighter = firefighterRepository.findByFirefighterUsername(username);

        if (firefighter != null) {
            return firefighter.getFirefighterName();
        }

        return null; // Return null if no firefighter is found for the given username
    }

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
}

package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.LoginRequest;
import com.piciu1221.firesignal.LoginResponse;
import com.piciu1221.firesignal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Delegate login logic to the UserService
        // Handle login request and return appropriate response
        // You may choose to return a JWT token, a session cookie, or any other response based on your application's requirements.
        // For simplicity, we'll just return a simple success message for now.
        LoginResponse loginResponse = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (loginResponse.isSuccess()) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}

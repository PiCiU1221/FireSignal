package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.FirefighterDTO;
import com.piciu1221.firesignal.dto.UserDTO;
import com.piciu1221.firesignal.dto.UsernameDTO;
import com.piciu1221.firesignal.exceptions.UserAlreadyExistException;
import com.piciu1221.firesignal.model.User;
import com.piciu1221.firesignal.service.SSEService;
import com.piciu1221.firesignal.service.UserService;

import com.piciu1221.firesignal.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

/**
 * Controller class for handling user-related API endpoints.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SSEService sseService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid UserDTO userDTO) {
        ApiResponse<String> response = userService.processLogin(userDTO);

        HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid UserDTO userDTO) {
        ApiResponse<String> response = userService.registerUser(userDTO);

        HttpStatus httpStatus = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(response);
    }

    /**
     * Endpoint for retrieving the firefighter name associated with a username.
     *
     * @param usernameDTO The request containing the username.
     * @return ResponseEntity with the firefighter name in a FirefighterDTO.
     */
    @PostMapping("/get-firefighter-name")
    public ResponseEntity<FirefighterDTO> getFirefighterName(@RequestBody UsernameDTO usernameDTO) {
        String firefighterName = userService.getFirefighterName(usernameDTO.getUsername());
        FirefighterDTO firefighterDTO = new FirefighterDTO(firefighterName);
        return ResponseEntity.ok(firefighterDTO);
    }

    /**
     * Endpoint for subscribing to real-time updates for alarmed firefighters.
     *
     * @param username The username of the user subscribing to updates.
     * @return Flux of SSE messages for real-time updates.
     */
    @GetMapping("/subscribe/{username}")
    public Flux<String> subscribeToAlarmedFirefighters(@PathVariable String username) {
        return sseService.subscribeToAlarmedFirefighters(username);
    }

    /**
     * Endpoint for checking if a user with the given username exists.
     * Used in new fire department creation
     *
     * @param username The username to check.
     * @return ResponseEntity with a boolean indicating whether the user exists.
     */
    @GetMapping("/check-user/{username}")
    public ResponseEntity<Boolean> checkIfUserExists(@PathVariable String username) {
        boolean userExists = userService.checkIfUserExists(username);
        return ResponseEntity.ok(userExists);
    }

    // Old testing user validation
    /*
    @PostMapping("/validate-user")
    public ResponseEntity<Boolean> validateUser(@RequestBody UserDTO userDTO) {
        boolean isValid = userService.loginUser(userDTO.getUsername(), userDTO.getPassword()).isSuccess();
        return ResponseEntity.ok(isValid);
    }
    */
}

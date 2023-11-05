package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.FirefighterDTO;
import com.piciu1221.firesignal.dto.UserDTO;
import com.piciu1221.firesignal.dto.UsernameDTO;
import com.piciu1221.firesignal.service.SSEService;
import com.piciu1221.firesignal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SSEService sseService;

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/get-firefighter-name")
    public ResponseEntity<FirefighterDTO> getFirefighterName(@RequestBody UsernameDTO usernameDTO) {
        String firefighterName = userService.getFirefighterName(usernameDTO.getUsername());
        FirefighterDTO firefighterDTO = new FirefighterDTO(firefighterName);
        return ResponseEntity.ok(firefighterDTO);
    }

    @GetMapping("/subscribe/{username}")
    public Flux<String> subscribeToAlarmedFirefighters(@PathVariable String username) {
        return sseService.subscribeToAlarmedFirefighters(username);
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

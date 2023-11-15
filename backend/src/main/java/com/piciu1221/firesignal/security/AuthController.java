package com.piciu1221.firesignal.security;

import com.piciu1221.firesignal.dto.UserDTO;
import com.piciu1221.firesignal.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller class responsible for handling authentication-related requests.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<String>> authenticate(@RequestBody @Valid UserDTO request) {
        ApiResponse<String> response = authService.authenticate(request);

        HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(response);
    }
}

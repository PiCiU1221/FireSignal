package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.UserRoleResponse;
import com.piciu1221.firesignal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserRoleController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-role")
    public ResponseEntity<UserRoleResponse> getUserRole(@RequestParam String username) {
        UserRoleResponse userRoleResponse = userService.getUserRole(username);
        return ResponseEntity.ok(userRoleResponse);
    }
}
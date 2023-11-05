package com.piciu1221.firesignal.dto;

import lombok.Data;

@Data
public class UserRoleResponse {
    private String role;

    public UserRoleResponse(String role) {
        this.role = role;
    }
}

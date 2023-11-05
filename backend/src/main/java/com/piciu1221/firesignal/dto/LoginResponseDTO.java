package com.piciu1221.firesignal.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private boolean success;
    private String message;

    public LoginResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

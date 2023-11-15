package com.piciu1221.firesignal.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;
}
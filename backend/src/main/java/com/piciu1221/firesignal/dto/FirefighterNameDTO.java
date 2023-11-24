package com.piciu1221.firesignal.dto;

import lombok.Data;

@Data
public class FirefighterNameDTO {
    private String firefighterName;

    public FirefighterNameDTO(String firefighterName) {
        this.firefighterName = firefighterName;
    }
}

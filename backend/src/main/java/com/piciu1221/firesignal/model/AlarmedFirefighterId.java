package com.piciu1221.firesignal.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class AlarmedFirefighterId implements Serializable {

    private Integer alarmId;
    private Integer firefighterId;

    public AlarmedFirefighterId(Integer alarmId, Integer firefighterId) {
        this.alarmId = alarmId;
        this.firefighterId = firefighterId;
    }
}

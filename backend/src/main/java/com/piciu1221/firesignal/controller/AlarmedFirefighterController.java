package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.AlarmInfoRequestDTO;
import com.piciu1221.firesignal.dto.ConsolidatedAlarmInfoDTO;
import com.piciu1221.firesignal.service.AlarmedFirefighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alarmed-firefighter")
public class AlarmedFirefighterController {
    @Autowired
    private AlarmedFirefighterService alarmedFirefighterService;

    @PostMapping("/get-consolidated-alarm-info")
    public ConsolidatedAlarmInfoDTO getConsolidatedInfo(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.getConsolidatedAlarmInfo(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }

    @PatchMapping("/update-acceptance/{alarmId}/{firefighterId}")
    public ResponseEntity<?> manageAlarmedFirefighter(
            @PathVariable Integer alarmId,
            @PathVariable Integer firefighterId,
            @RequestBody Boolean accept
    ) {
        if (accept == null) {
            return ResponseEntity.badRequest().body("Action not specified");
        }

        alarmedFirefighterService.updateAcceptanceStatus(alarmId, firefighterId, accept);
        return ResponseEntity.ok().build();
    }

    /*
    @PatchMapping("/accept-alarm/{alarmId}/{firefighterId}")
    public ResponseEntity<?> acceptAlarmedFirefighter(@PathVariable Integer alarmId, @PathVariable Integer firefighterId) {
        alarmedFirefighterService.updateAcceptanceStatus(alarmId, firefighterId, true);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/decline-alarm/{alarmId}/{firefighterId}")
    public ResponseEntity<?> declineAlarmedFirefighter(@PathVariable Integer alarmId, @PathVariable Integer firefighterId) {
        alarmedFirefighterService.updateAcceptanceStatus(alarmId, firefighterId, false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-alarmed-firefighters-count")
    public int getAlarmedFirefightersCount(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.getAlarmedFirefightersCount(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }

    @PostMapping("/has-accepted-commander")
    public boolean hasAcceptedCommander(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.hasAcceptedCommander(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }

    @PostMapping("/get-accepted-drivers-count")
    public int getAcceptedDriversCount(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.getAcceptedDriversCount(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }

    @PostMapping("/get-accepted-firefighters-count")
    public int getAcceptedFirefightersCount(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.getAcceptedFirefightersCount(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }

    @PostMapping("/has-accepted-technical-rescue")
    public boolean hasAcceptedTechnicalRescue(@RequestBody AlarmInfoRequestDTO alarmInfoRequestDTO) {
        return alarmedFirefighterService.hasAcceptedTechnicalRescue(alarmInfoRequestDTO.getAlarmId(), alarmInfoRequestDTO.getFirefighterUsername());
    }
    */

    /*
    @GetMapping("/test")
    public ResponseEntity<String> testSSE() {
        String message = "Id: 77" +
                ", City: Stargard" +
                ", Street: Wyszynskiego 10" +
                ", Description: Flames are visible from the windows on the upper floors. The roof is collapsing, and there is heavy smoke billowing from the structure. Occupants have evacuated the building, and the situation is critical.";

        sseService.sendSseMessageToUser("mdrogosz", message);

        return ResponseEntity.ok("Test alarm message sent: " + message);
    }
    */
}

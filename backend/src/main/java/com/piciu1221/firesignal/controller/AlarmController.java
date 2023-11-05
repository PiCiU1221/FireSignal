package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.AlarmDisplayRequestDTO;
import com.piciu1221.firesignal.model.*;
import com.piciu1221.firesignal.repository.AlarmRepository;
import com.piciu1221.firesignal.repository.AlarmedFirefighterRepository;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.service.AlarmService;
import com.piciu1221.firesignal.service.SSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private FirefighterRepository firefighterRepository;
    @Autowired
    private AlarmedFirefighterRepository alarmedFirefighterRepository;

    @Autowired
    private SSEService sseService;

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchAlarm(@RequestBody AlarmData alarmData) {
        // Call the service layer method to create and dispatch the alarm
        Alarm savedAlarm = alarmService.createAndDispatchAlarm(alarmData);

        // Return a success response
        return ResponseEntity.ok("Alarm dispatched successfully");
    }

    @PostMapping("/get-alarms-for-firefighter")
    public List<Alarm> getAlarmsForFirefighter(@RequestBody AlarmDisplayRequestDTO alarmDisplayRequestDTO) {
        return alarmService.getAlarmsForFirefighter(alarmDisplayRequestDTO.getUsername(), alarmDisplayRequestDTO.getSkip(), alarmDisplayRequestDTO.getHowMuch());
    }

    @GetMapping("/get-alarms-pages")
    public ResponseEntity<List<AlarmWithFireDepartments>> getFireDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int alarmsPerPage // Default to 6 alarms per page
    ) {
        // Fetch the latest alarms with fire departments using pagination
        List<AlarmWithFireDepartments> alarmsWithDepartmentsPage = alarmService.getLatestAlarmsWithDepartments(page, alarmsPerPage);

        return ResponseEntity.ok(alarmsWithDepartmentsPage);
    }
}

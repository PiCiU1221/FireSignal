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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AlarmController {

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private FirefighterRepository firefighterRepository;
    @Autowired
    private AlarmedFirefighterRepository alarmedFirefighterRepository;

    @Autowired
    private SSEService sseService;

    private final AlarmService alarmDisplayService;

    @Autowired
    public AlarmController(AlarmService alarmDisplayService) {
        this.alarmDisplayService = alarmDisplayService;
    }


    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchAlarm(@RequestBody AlarmData alarmData) {
        // Create a new Alarm entity
        Alarm newAlarm = new Alarm();
        newAlarm.setAlarmCity(alarmData.getCity());
        newAlarm.setAlarmStreet(alarmData.getStreet());
        newAlarm.setAlarmDescription(alarmData.getDescription());

        // Save the new alarm to the database
        Alarm savedAlarm = alarmRepository.save(newAlarm);

        // Get firefighter ids from the selected fire departments
        List<Integer> selectedDepartmentIds = alarmData.getSelectedDepartments();
        List<Firefighter> selectedFirefighters = firefighterRepository.findByFireDepartment_DepartmentIdIn(selectedDepartmentIds);

        // Link the alarm with the selected firefighters
        List<AlarmedFirefighter> alarmedFirefighters = new ArrayList<>();
        for (Firefighter firefighter : selectedFirefighters) {
            AlarmedFirefighter alarmedFirefighter = new AlarmedFirefighter();

            // Create and set the embedded ID
            AlarmedFirefighterId id = new AlarmedFirefighterId();
            id.setAlarmId(savedAlarm.getAlarmId()); // Set the alarm ID
            id.setFirefighterId(firefighter.getFirefighterId()); // Set the firefighter ID
            alarmedFirefighter.setId(id);

            // Set the related alarm and firefighter
            alarmedFirefighter.setAlarm(savedAlarm);
            alarmedFirefighter.setFirefighter(firefighter);

            alarmedFirefighters.add(alarmedFirefighter);
            String username = alarmedFirefighter.getFirefighter().getFirefighterUsername();
            String message = "Id: " + savedAlarm.getAlarmId() + ", FirefighterId: " + firefighter.getFirefighterId() +
                    ", City: " + savedAlarm.getAlarmCity() + ", Street: " + savedAlarm.getAlarmStreet() +
                    ", Description: " + savedAlarm.getAlarmDescription();
            sseService.sendSseMessageToUser(username, message);
        }

        // Save the link records to the database
        alarmedFirefighterRepository.saveAll(alarmedFirefighters);

        // Return a success message
        return ResponseEntity.ok("Alarm dispatched successfully");
    }

    @PostMapping("/get-alarms-for-firefighter")
    public List<Alarm> getAlarmsForFirefighter(@RequestBody AlarmDisplayRequestDTO alarmDisplayRequestDTO) {
        return alarmDisplayService.getAlarmsForFirefighter(alarmDisplayRequestDTO.getUsername(), alarmDisplayRequestDTO.getSkip(), alarmDisplayRequestDTO.getHowMuch());
    }
}

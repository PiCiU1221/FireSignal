package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.AlarmDisplayRequestDTO;
import com.piciu1221.firesignal.entity.*;
import com.piciu1221.firesignal.repository.AlarmRepository;
import com.piciu1221.firesignal.repository.AlarmedFirefighterRepository;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.service.AlarmService;
import com.piciu1221.firesignal.service.SSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling alarm-related HTTP requests.
 */
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

    /**
     * Endpoint for dispatching a new alarm.
     *
     * @param alarmData The data for the new alarm.
     * @return A response indicating the success of the dispatch.
     */
    @PostMapping("/dispatch")
    public ResponseEntity<String> dispatchAlarm(@RequestBody AlarmData alarmData) {
        // Call the service layer method to create and dispatch the alarm
        Alarm savedAlarm = alarmService.createAndDispatchAlarm(alarmData);

        // Return a success response
        return ResponseEntity.ok("Alarm dispatched successfully");
    }

    /**
     * Endpoint for retrieving alarms for a specific firefighter.
     *
     * @param alarmDisplayRequestDTO The request DTO containing parameters for fetching alarms.
     * @return A list of alarms for the specified firefighter.
     */
    @PostMapping("/get-alarms-for-firefighter")
    public List<Alarm> getAlarmsForFirefighter(@RequestBody AlarmDisplayRequestDTO alarmDisplayRequestDTO) {
        return alarmService.getAlarmsForFirefighter(alarmDisplayRequestDTO.getUsername(), alarmDisplayRequestDTO.getSkip(), alarmDisplayRequestDTO.getHowMuch());
    }

    /**
     * Endpoint for fetching a paginated list of the latest alarms with associated fire departments.
     *
     * @param page           The page number.
     * @param alarmsPerPage  The number of alarms per page.
     * @return A response containing a list of alarms with associated fire departments.
     */
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

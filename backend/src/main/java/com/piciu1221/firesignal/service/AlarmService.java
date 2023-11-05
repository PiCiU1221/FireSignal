package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.model.*;
import com.piciu1221.firesignal.repository.AlarmRepository;
import com.piciu1221.firesignal.repository.AlarmedFirefighterRepository;
import com.piciu1221.firesignal.repository.FireDepartmentRepository;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final FireDepartmentRepository fireDepartmentRepository;
    private final AlarmedFirefighterRepository alarmedFirefighterRepository;
    private final FirefighterRepository firefighterRepository;

    @Autowired
    private SSEService sseService;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository, AlarmedFirefighterRepository alarmedFirefighterRepository,
                        FireDepartmentRepository fireDepartmentRepository, FirefighterRepository firefighterRepository) {
        this.alarmRepository = alarmRepository;
        this.fireDepartmentRepository = fireDepartmentRepository;
        this.alarmedFirefighterRepository = alarmedFirefighterRepository;
        this.firefighterRepository = firefighterRepository;
    }

    public Alarm createAndDispatchAlarm(AlarmData alarmData) {
        // Create a new alarm based on the provided data
        Alarm newAlarm = new Alarm();
        newAlarm.setAlarmCity(alarmData.getCity());
        newAlarm.setAlarmStreet(alarmData.getStreet());
        newAlarm.setAlarmDescription(alarmData.getDescription());

        // Save the newly created alarm
        Alarm savedAlarm = alarmRepository.save(newAlarm);

        // Retrieve firefighters from the selected departments based on the provided department IDs
        List<Integer> selectedDepartmentIds = alarmData.getSelectedDepartments();
        List<Firefighter> allSelectedFirefighters = firefighterRepository.findByFireDepartment_DepartmentIdIn(selectedDepartmentIds);

        // Dispatch the newly created alarm to the selected firefighters
        dispatchAlarm(savedAlarm, allSelectedFirefighters);

        // Return the saved alarm
        return savedAlarm;
    }

    public void dispatchAlarm(Alarm savedAlarm, List<Firefighter> selectedFirefighters) {
        List<Firefighter> activeSelectedFirefighters = selectedFirefighters.stream()
                .filter(firefighter -> sseService.isUserActive(firefighter.getFirefighterUsername()))
                .toList();

        List<AlarmedFirefighter> alarmedFirefighters = new ArrayList<>();
        for (Firefighter firefighter : activeSelectedFirefighters) {
            AlarmedFirefighter alarmedFirefighter = new AlarmedFirefighter();
            AlarmedFirefighterId id = new AlarmedFirefighterId();
            id.setAlarmId(savedAlarm.getAlarmId());
            id.setFirefighterId(firefighter.getFirefighterId());
            alarmedFirefighter.setId(id);
            alarmedFirefighter.setAlarm(savedAlarm);
            alarmedFirefighter.setFirefighter(firefighter);
            alarmedFirefighters.add(alarmedFirefighter);

            String username = firefighter.getFirefighterUsername();
            String message = "Id: " + savedAlarm.getAlarmId() + ", FirefighterId: " + firefighter.getFirefighterId() +
                    ", City: " + savedAlarm.getAlarmCity() + ", Street: " + savedAlarm.getAlarmStreet() +
                    ", Description: " + savedAlarm.getAlarmDescription();
            sseService.sendSseMessageToUser(username, message);
        }

        alarmedFirefighterRepository.saveAll(alarmedFirefighters);
    }

    public List<Alarm> getAlarmsForFirefighter(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alarmRepository.findAlarmsForFirefighter(username, pageable);
    }

    public List<AlarmWithFireDepartments> getLatestAlarmsWithDepartments(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("alarmId").descending());

        Page<Alarm> alarmPage = alarmRepository.findAll(pageable);

        // Initialize a list to store alarms with fire departments
        List<AlarmWithFireDepartments> alarmsWithDepartments = new ArrayList<>();

        // Iterate over the latest alarms
        for (Alarm alarm : alarmPage.getContent()) {
            // Fetch the alerted fire departments for this alarm
            List<FireDepartment> alertedDepartments = fireDepartmentRepository.findFireDepartmentsByAlarmId(alarm.getAlarmId());

            // Create an AlarmWithFireDepartments object to combine alarm and fire departments
            AlarmWithFireDepartments alarmWithDepartments = new AlarmWithFireDepartments(alarm, alertedDepartments);

            // Add the combination to the list
            alarmsWithDepartments.add(alarmWithDepartments);
        }

        return alarmsWithDepartments;
    }

    // Old method
    /*
    public List<Alarm> getAlarmsByPage(int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("departmentId").descending());
        Page<Alarm> alarmPage = alarmRepository.findAll(pageable);
        return alarmPage.getContent();
    }
    */
}

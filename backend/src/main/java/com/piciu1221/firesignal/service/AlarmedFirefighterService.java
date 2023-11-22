package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.dto.ConsolidatedAlarmInfoDTO;
import com.piciu1221.firesignal.entity.AlarmedFirefighter;
import com.piciu1221.firesignal.entity.AlarmedFirefighterId;
import com.piciu1221.firesignal.repository.AlarmedFirefighterRepository;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing alarmed firefighter-related operations.
 */
@Service
public class AlarmedFirefighterService {
    @Autowired
    private AlarmedFirefighterRepository alarmedFirefighterRepository;
    @Autowired
    private FirefighterRepository firefighterRepository;

    /**
     * Retrieves consolidated information about alarmed firefighters for a given alarm and department.
     *
     * @param alarmId             The ID of the alarm.
     * @param firefighterUsername The username of the firefighter.
     * @return ConsolidatedAlarmInfoDTO containing information about alarmed firefighters.
     */
    public ConsolidatedAlarmInfoDTO getConsolidatedAlarmInfo(int alarmId, String firefighterUsername) {
        // Retrieve the department ID for the given firefighter username
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);

        // Retrieve various counts and flags related to alarmed firefighters for a given department and alarm
        int count = alarmedFirefighterRepository.findCountByDepartmentIdAndAlarmId(departmentId, alarmId);
        boolean hasAcceptedCommander = alarmedFirefighterRepository.hasAcceptedCommander(departmentId, alarmId);
        int acceptedDriversCount = alarmedFirefighterRepository.getAcceptedDriversCount(departmentId, alarmId);
        int acceptedFirefightersCount = alarmedFirefighterRepository.getAcceptedFirefightersCount(departmentId, alarmId);
        boolean hasAcceptedTechnicalRescue = alarmedFirefighterRepository.hasAcceptedTechnicalRescue(departmentId, alarmId);

        // Create and return a ConsolidatedAlarmInfoDTO with the retrieved information
        return new ConsolidatedAlarmInfoDTO(count, hasAcceptedCommander, acceptedDriversCount, acceptedFirefightersCount, hasAcceptedTechnicalRescue);
    }

    /**
     * Updates the acceptance status of an alarmed firefighter for a given alarm.
     *
     * @param alarmId      The ID of the alarm.
     * @param firefighterId The ID of the firefighter.
     * @param isAccepted   The new acceptance status.
     */
    public void updateAcceptanceStatus(Integer alarmId, Integer firefighterId, boolean isAccepted) {
        AlarmedFirefighter alarmedFirefighter = alarmedFirefighterRepository.findById(new AlarmedFirefighterId(alarmId, firefighterId))
                .orElseThrow(() -> new EntityNotFoundException("AlarmedFirefighter not found"));

        alarmedFirefighter.setAccepted(isAccepted);
        alarmedFirefighterRepository.save(alarmedFirefighter);
    }

    /*
    public void acceptFirefighter(Integer alarmId, Integer firefighterId) {
        AlarmedFirefighter alarmedFirefighter = new AlarmedFirefighter();

        AlarmedFirefighterId id = new AlarmedFirefighterId();
        id.setAlarmId(alarmId);
        id.setFirefighterId(firefighterId);
        alarmedFirefighter.setId(id);

        alarmedFirefighter.setAccepted(true);
        alarmedFirefighterRepository.save(alarmedFirefighter);
    }

    public void declineFirefighter(Integer alarmId, Integer firefighterId) {
        AlarmedFirefighter alarmedFirefighter = new AlarmedFirefighter();

        AlarmedFirefighterId id = new AlarmedFirefighterId();
        id.setAlarmId(alarmId);
        id.setFirefighterId(firefighterId);
        alarmedFirefighter.setId(id);

        alarmedFirefighter.setAccepted(false);
        alarmedFirefighterRepository.save(alarmedFirefighter);
    }

    public int getAlarmedFirefightersCount(int alarmId, String firefighterUsername) {
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);
        return alarmedFirefighterRepository.findCountByDepartmentIdAndAlarmId(departmentId, alarmId);
    }

    public boolean hasAcceptedCommander(int alarmId, String firefighterUsername) {
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);
        return alarmedFirefighterRepository.hasAcceptedCommander(departmentId, alarmId);
    }

    public int getAcceptedDriversCount(int alarmId, String firefighterUsername) {
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);
        return alarmedFirefighterRepository.getAcceptedDriversCount(departmentId, alarmId);
    }

    public int getAcceptedFirefightersCount(int alarmId, String firefighterUsername) {
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);
        return alarmedFirefighterRepository.getAcceptedFirefightersCount(departmentId, alarmId);
    }

    public boolean hasAcceptedTechnicalRescue(int alarmId, String firefighterUsername) {
        Integer departmentId = firefighterRepository.findDepartmentIdByUsername(firefighterUsername);
        return alarmedFirefighterRepository.hasAcceptedTechnicalRescue(departmentId, alarmId);
    }
    */

    /* SSE testing
    public Flux<String> subscribeToAlarmedFirefighters(String username) {
        System.out.println("Subscribing to SSE for user: " + username);

        return Flux.create(sink -> {
            System.out.println("SSE subscription started for user: " + username);

            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        // Emit SSE message with a timestamp
                        String message = "New record added at " + LocalDateTime.now();
                        System.out.println("Emitting SSE message: " + message);
                        sink.next(message);

                        Thread.sleep(5000); // Sleep for 5 seconds
                    }
                } catch (InterruptedException e) {
                    // Thread was interrupted, which means the connection was closed
                    System.out.println("SSE subscription interrupted for user: " + username);
                    Thread.currentThread().interrupt();
                }
            });

            thread.start();

            // Add a hook to stop the thread when the Flux terminates
            sink.onDispose(() -> {
                System.out.println("Disposing SSE subscription for user: " + username);
                thread.interrupt();
            });
        });
    } */
}

package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.entity.AlarmedFirefighter;
import com.piciu1221.firesignal.entity.AlarmedFirefighterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing alarmed firefighter data in the database.
 */
@Repository
public interface AlarmedFirefighterRepository extends JpaRepository<AlarmedFirefighter, AlarmedFirefighterId> {

    /**
     * Count the number of alarmed firefighters for a specific department and alarm.
     *
     * @param departmentId The ID of the fire department.
     * @param alarmId      The ID of the alarm.
     * @return The count of alarmed firefighters for the given department and alarm.
     */
    @Query("SELECT COUNT(af) FROM AlarmedFirefighter af " +
            "INNER JOIN af.firefighter f " +
            "INNER JOIN af.alarm a " +
            "WHERE f.fireDepartment.departmentId = :departmentId " +
            "AND a.alarmId = :alarmId")
    int findCountByDepartmentIdAndAlarmId(Integer departmentId, int alarmId);

    /**
     * Check if there is an accepted commander for a specific department and alarm.
     *
     * @param departmentId The ID of the fire department.
     * @param alarmId      The ID of the alarm.
     * @return True if there is an accepted commander, false otherwise.
     */
    @Query("SELECT COUNT(af) > 0 FROM AlarmedFirefighter af " +
            "INNER JOIN af.firefighter f " +
            "WHERE f.fireDepartment.departmentId = :departmentId " +
            "AND af.alarm.alarmId = :alarmId " +
            "AND af.accepted = true " +
            "AND f.firefighterCommander = true")
    boolean hasAcceptedCommander(Integer departmentId, int alarmId);

    /**
     * Get the count of accepted drivers for a specific department and alarm.
     *
     * @param departmentId The ID of the fire department.
     * @param alarmId      The ID of the alarm.
     * @return The count of accepted drivers for the given department and alarm.
     */
    @Query("SELECT COUNT(af) FROM AlarmedFirefighter af " +
            "INNER JOIN af.firefighter f " +
            "WHERE f.fireDepartment.departmentId = :departmentId " +
            "AND f.firefighterDriver = true " +
            "AND af.alarm.alarmId = :alarmId " +
            "AND af.accepted = true ")
    int getAcceptedDriversCount(Integer departmentId, int alarmId);

    /**
     * Get the count of accepted firefighters for a specific department and alarm.
     *
     * @param departmentId The ID of the fire department.
     * @param alarmId      The ID of the alarm.
     * @return The count of accepted firefighters for the given department and alarm.
     */
    @Query("SELECT COUNT(af) FROM AlarmedFirefighter af " +
            "INNER JOIN af.firefighter f " +
            "WHERE f.fireDepartment.departmentId = :departmentId " +
            "AND af.alarm.alarmId = :alarmId " +
            "AND af.accepted = true ")
    int getAcceptedFirefightersCount(Integer departmentId, int alarmId);

    /**
     * Check if there is an accepted technical rescue firefighter for a specific department and alarm.
     *
     * @param departmentId The ID of the fire department.
     * @param alarmId      The ID of the alarm.
     * @return True if there is an accepted technical rescue firefighter, false otherwise.
     */
    @Query("SELECT COUNT(af) > 0 FROM AlarmedFirefighter af " +
            "INNER JOIN af.firefighter f " +
            "WHERE f.fireDepartment.departmentId = :departmentId " +
            "AND af.alarm.alarmId = :alarmId " +
            "AND af.accepted = true " +
            "AND f.firefighterTechnicalRescue = true")
    boolean hasAcceptedTechnicalRescue(Integer departmentId, int alarmId);
}
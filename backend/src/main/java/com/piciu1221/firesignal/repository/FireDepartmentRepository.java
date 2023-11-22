package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.entity.FireDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FireDepartmentRepository extends JpaRepository<FireDepartment, Long> {

    /**
     * Retrieve a list of fire departments associated with a specific alarm.
     *
     * @param alarmId The ID of the alarm.
     * @return List of fire departments associated with the given alarm.
     */
    @Query( "SELECT DISTINCT fd " +
            "FROM AlarmedFirefighter af " +
            "INNER JOIN Firefighter f ON af.firefighter = f " +
            "INNER JOIN FireDepartment fd ON f.fireDepartment = fd " +
            "WHERE af.alarm.alarmId = :alarmId")
    List<FireDepartment> findFireDepartmentsByAlarmId(int alarmId);

    /**
     * Check if a fire department with the given name exists in the database.
     *
     * @param departmentName The name of the fire department.
     * @return True if a fire department with the given name exists, false otherwise.
     */
    boolean existsByDepartmentName(String departmentName);
}

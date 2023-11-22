package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing alarm data in the database.
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    /**
     * Retrieve a list of alarms associated with a specific firefighter.
     *
     * @param firefighterUsername The username of the firefighter.
     * @param pageable            Pagination information.
     * @return List of alarms associated with the given firefighter, ordered by date and time (descending).
     */
    @Query("SELECT a FROM Alarm a " +
            "JOIN AlarmedFirefighter af ON a.alarmId = af.alarm.alarmId " +
            "JOIN Firefighter f ON af.firefighter.firefighterId = f.firefighterId " +
            "WHERE f.firefighterUsername = :firefighterUsername " +
            "ORDER BY a.dateTime DESC")
    List<Alarm> findAlarmsForFirefighter(String firefighterUsername,
                                         Pageable pageable);
}
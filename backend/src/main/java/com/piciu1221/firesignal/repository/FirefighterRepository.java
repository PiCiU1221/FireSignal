package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.entity.Firefighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing firefighter data in the database.
 */
@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Long> {

    /**
     * Retrieve a list of firefighters associated with the specified fire department IDs.
     *
     * @param departmentIds List of fire department IDs.
     * @return List of firefighters associated with the specified fire department IDs.
     */
    List<Firefighter> findByFireDepartment_DepartmentIdIn(List<Integer> departmentIds);

    /**
     * Retrieve a firefighter by their username.
     *
     * @param firefighterUsername The username of the firefighter.
     * @return The firefighter with the specified username.
     */
    Firefighter findByFirefighterUsername(String firefighterUsername);

    /**
     * Find the department ID associated with a firefighter's username.
     *
     * @param username The username of the firefighter.
     * @return The department ID associated with the given firefighter's username.
     */
    @Query("SELECT f.fireDepartment.departmentId FROM Firefighter f WHERE f.firefighterUsername = :username")
    Integer findDepartmentIdByUsername(String username);

    boolean existsByFirefighterUsername(String firefighterUsername);
}

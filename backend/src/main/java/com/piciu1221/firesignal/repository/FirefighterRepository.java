package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.entity.Firefighter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing firefighter data in the database.
 */
@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Long> {

    List<Firefighter> findByFireDepartment_DepartmentIdIn(List<Integer> departmentIds);

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

    Page<Firefighter> findByFireDepartmentDepartmentId(Long departmentId, Pageable pageable);

    /**
     * Find the department ID associated with a firefighter's username.
     *
     * @param username The username of the firefighter.
     * @return The department ID associated with the given firefighter's username.
     */
    @Query("SELECT f.fireDepartment.departmentId FROM Firefighter f WHERE f.firefighterUsername = :username")
    Long findFireDepartmentIdByUsername(@Param("username") String username);
}

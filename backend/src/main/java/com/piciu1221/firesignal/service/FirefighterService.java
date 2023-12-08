package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.dto.FirefighterAddDTO;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.entity.User;
import com.piciu1221.firesignal.repository.FireDepartmentRepository;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.repository.UserRepository;
import com.piciu1221.firesignal.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;
    private final FireDepartmentRepository fireDepartmentRepository;
    private final UserRepository userRepository;


    @Autowired
    public FirefighterService(FirefighterRepository firefighterRepository, FireDepartmentRepository fireDepartmentRepository, UserRepository userRepository) {
        this.firefighterRepository = firefighterRepository;
        this.fireDepartmentRepository = fireDepartmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Add a firefighter using the provided DTO.
     *
     * @param addDTO The DTO containing firefighter information.
     * @return ApiResponse with the result message.
     */
    @Transactional
    public ApiResponse<String> addFirefighter(FirefighterAddDTO addDTO) {
        // Convert DTO to Firefighter entity
        Firefighter firefighter = addDTO.createFirefighterEntity(fireDepartmentRepository);

        // Call the original addFirefighter method with the entity
        return addFirefighter(firefighter);
    }

    /**
     * Add a firefighter with a specified username using the provided DTO.
     *
     * @param addDTO   The DTO containing firefighter information.
     * @param username The username associated with the firefighter.
     * @return ApiResponse with the result message.
     */
    @Transactional
    public ApiResponse<String> addFirefighterWithUsername(FirefighterAddDTO addDTO, String username) {
        // Convert DTO to Firefighter entity
        Firefighter firefighter = addDTO.createFirefighterEntity(fireDepartmentRepository, username);

        // Call the original addFirefighter method with the entity
        return addFirefighter(firefighter);
    }

    /**
     * Add a firefighter using the provided entity.
     *
     * @param firefighter The firefighter entity to be added.
     * @return ApiResponse with the result message.
     */
    @Transactional
    public ApiResponse<String> addFirefighter(Firefighter firefighter) {
        try {
            // Check if the username is unique
            if (firefighterRepository.existsByFirefighterUsername(firefighter.getFirefighterUsername())) {
                throw new IllegalArgumentException("Firefighter with this username already exists");
            }

            // Check if the user with the specified username exists
            if (!userRepository.existsByUsername(firefighter.getFirefighterUsername())) {
                throw new IllegalArgumentException("User with this username does not exist");
            }

            // Save the firefighter using the repository
            firefighterRepository.save(firefighter);

            return ApiResponse.success("Firefighter added successfully");
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Get a list of firefighters with pagination.
     *
     * @param page The page number for pagination (default is 0).
     * @return List of firefighters for the specified page.
     */
    public List<Firefighter> getFirefightersByPage(int page) {
        // Define the number of items per page
        int pageSize = 6;

        // Create a Pageable object for pagination with sorting by firefighterId in descending order
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("firefighterId").descending());

        // Retrieve a page of firefighters from the repository
        Page<Firefighter> firefighterPage = firefighterRepository.findAll(pageable);

        // Return the content (list of firefighters) from the retrieved page
        return firefighterPage.getContent();
    }

    /**
     * Get a list of firefighters based on the specified username and pagination.
     *
     * @param page     The page number for pagination (default is 0).
     * @param username The username associated with the firefighters.
     * @return List of firefighters for the specified username and page.
     */
    public List<Firefighter> getFirefightersByUsernameAndPage(int page, String username) {
        // Get the fire department ID based on the username
        Long departmentId = firefighterRepository.findFireDepartmentIdByUsername(username);

        // If the departmentId is not null, retrieve a page of firefighters from the repository for that department
        if (departmentId != null) {
            // Define the number of items per page
            int pageSize = 6;

            // Create a Pageable object for pagination with sorting by firefighterId in descending order
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("firefighterId").descending());

            // Retrieve a page of firefighters from the repository for the specific department
            Page<Firefighter> firefighterPage = firefighterRepository.findByFireDepartmentDepartmentId(departmentId, pageable);

            // Return the content (list of firefighters) from the retrieved page
            return firefighterPage.getContent();
        } else {
            // Handle the case where the departmentId is not found (e.g., return an empty list or throw an exception)
            return Collections.emptyList();
        }
    }

    /**
     * Check if a firefighter with the given username has a department assigned.
     *
     * @param username The username of the firefighter.
     * @return True if the firefighter has a department assigned, false otherwise.
     */
    public boolean hasDepartmentAssigned(String username) {
        // Check if a firefighter with the given username exists
        boolean firefighterExists = firefighterRepository.existsByFirefighterUsername(username);

        // If the firefighter doesn't exist, return false
        if (!firefighterExists) {
            return false;
        }

        // Retrieve the firefighter to check if it has a fire department assigned
        Firefighter firefighter = firefighterRepository.findByFirefighterUsername(username);

        // Check if the firefighter has a fire department assigned
        return firefighter != null && firefighter.getFireDepartment() != null;
    }

    /**
     * Delete a firefighter with the specified username.
     *
     * @param username The username of the firefighter to be deleted.
     * @return ApiResponse with the result message.
     */
    public ApiResponse<String> deleteFirefighterByUsername(String username) {
        try {
            Firefighter firefighter = firefighterRepository.findByFirefighterUsername(username);

            if (firefighter != null) {
                firefighterRepository.delete(firefighter);
                return ApiResponse.success("Firefighter deleted successfully");
            } else {
                return ApiResponse.error("Firefighter with the specified username not found");
            }
        } catch (Exception e) {
            return ApiResponse.error("Error deleting firefighter: " + e.getMessage());
        }
    }
}

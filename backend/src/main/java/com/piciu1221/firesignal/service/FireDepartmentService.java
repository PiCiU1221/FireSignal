package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.entity.FireDepartment;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.repository.FireDepartmentRepository;
import com.piciu1221.firesignal.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing fire department-related operations.
 */
@Service
public class FireDepartmentService {

    private final FireDepartmentRepository fireDepartmentRepository;
    private final FirefighterService firefighterService;
    private final UserService userService;

    @Autowired
    public FireDepartmentService(
            FireDepartmentRepository fireDepartmentRepository,
            FirefighterService firefighterService,
            UserService userService) {
        this.fireDepartmentRepository = fireDepartmentRepository;
        this.firefighterService = firefighterService;
        this.userService = userService;
    }

    public List<FireDepartment> getAllFireDepartments() {
        return fireDepartmentRepository.findAll();
    }

    /**
     * Retrieves a paginated list of fire departments sorted by departmentId in descending order.
     *
     * @param page The page number to retrieve.
     * @return List of FireDepartment objects representing a page of fire departments.
     */
    public List<FireDepartment> getFireDepartmentsByPage(int page) {
        // Define the number of items per page
        int pageSize = 8;

        // Create a Pageable object for pagination with sorting by departmentId in descending order
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("departmentId").descending());

        // Retrieve a page of fire departments from the repository
        Page<FireDepartment> fireDepartmentPage = fireDepartmentRepository.findAll(pageable);

        // Return the content (list of fire departments) from the retrieved page
        return fireDepartmentPage.getContent();
    }

    @Transactional
    public ApiResponse<String> createDepartmentAndChief(FireDepartment fireDepartment, Firefighter firefighter) {
        try {
            // Check if the username is unique
            if (fireDepartmentRepository.existsByDepartmentName(fireDepartment.getDepartmentName())) {
                throw new IllegalArgumentException("Fire Department with this name already exists");
            }

            // Save the fire department
            FireDepartment savedDepartment = fireDepartmentRepository.save(fireDepartment);

            // Set the relationship between FireDepartment and Firefighter
            firefighter.setFireDepartment(savedDepartment);

            // Save the firefighter using the FirefighterService
            ApiResponse<String> addFirefighterResponse = firefighterService.addFirefighter(firefighter);

            // Check if adding the firefighter was successful
            if (addFirefighterResponse.isSuccess()) {
                // Set user role to "COMMANDER"
                userService.setUserRole(firefighter.getFirefighterUsername(), "COMMANDER");

                return ApiResponse.success("Department and Chief created successfully");
            } else {
                // Return an error response if adding the firefighter failed
                return ApiResponse.error(addFirefighterResponse.getMessage());
            }
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    public List<FireDepartment> getNearestFireDepartments(double latitude, double longitude) {
        return fireDepartmentRepository.findNearestFireDepartments(latitude, longitude);
    }
}

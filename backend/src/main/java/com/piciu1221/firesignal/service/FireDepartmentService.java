package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.model.FireDepartment;
import com.piciu1221.firesignal.repository.FireDepartmentRepository;
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

    @Autowired
    public FireDepartmentService(FireDepartmentRepository fireDepartmentRepository) {
        this.fireDepartmentRepository = fireDepartmentRepository;
    }

    /**
     * Retrieves a list of all fire departments.
     *
     * @return List of FireDepartment objects representing all fire departments.
     */
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

    /**
     * Creates a new fire department.
     *
     * @param fireDepartment The FireDepartment object representing the new fire department.
     * @return The created FireDepartment object.
     * @throws IllegalArgumentException if the department name is not unique.
     */
    public FireDepartment createFireDepartment(FireDepartment fireDepartment) {
        // Check if the department name is unique
        if (fireDepartmentRepository.existsByDepartmentName(fireDepartment.getDepartmentName())) {
            throw new IllegalArgumentException("Department name must be unique");
        }

        // Save the fire department using the repository
        return fireDepartmentRepository.save(fireDepartment);
    }
}

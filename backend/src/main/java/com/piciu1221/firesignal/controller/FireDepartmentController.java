package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.service.FireDepartmentService;
import com.piciu1221.firesignal.model.FireDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for handling Fire Department related API endpoints.
 */
@RestController
@RequestMapping("/api/fire-departments")
public class FireDepartmentController {
    private final FireDepartmentService fireDepartmentService;

    @Autowired
    public FireDepartmentController(FireDepartmentService fireDepartmentService) {
        this.fireDepartmentService = fireDepartmentService;
    }

    /**
     * Get a paginated list of fire departments.
     *
     * @param page The page number for pagination (default is 0).
     * @return ResponseEntity containing the list of fire departments and HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<FireDepartment>> getFireDepartments(@RequestParam(defaultValue = "0") int page) {
        // Implement pagination logic here using the page parameter
        List<FireDepartment> fireDepartments = fireDepartmentService.getFireDepartmentsByPage(page);
        return ResponseEntity.ok(fireDepartments);
    }

    /**
     * Get a list of all fire departments.
     *
     * @return List of all fire departments.
     */
    @GetMapping("/all")
    public List<FireDepartment> getAllFireDepartments() {
        return fireDepartmentService.getAllFireDepartments();
    }

    /**
     * Create a new fire department.
     *
     * @param fireDepartment The fire department to be created.
     * @return ResponseEntity containing the created fire department and HTTP status.
     */
    @PostMapping
    public ResponseEntity<FireDepartment> createFireDepartment(@RequestBody @Valid FireDepartment fireDepartment) {
        FireDepartment createdFireDepartment = fireDepartmentService.createFireDepartment(fireDepartment);
        return new ResponseEntity<>(createdFireDepartment, HttpStatus.CREATED);
    }
}

package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.FireDepartmentAddDTO;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.service.FireDepartmentService;
import com.piciu1221.firesignal.entity.FireDepartment;
import com.piciu1221.firesignal.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> createDepartmentAndChief(@RequestBody FireDepartmentAddDTO addDTO) {
        try {
            // Create FireDepartment and Firefighter entities from the DTO
            FireDepartment fireDepartment = addDTO.createFireDepartmentEntity();
            Firefighter firefighter = addDTO.createFirefighterEntity();

            // Call the service method to create department and chief
            ApiResponse<String> response = fireDepartmentService.createDepartmentAndChief(fireDepartment, firefighter);

            // Determine HTTP status based on the success flag in the response
            HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

            // Return ResponseEntity with the ApiResponse and appropriate HTTP status
            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an ApiResponse with an error message
            ApiResponse<String> errorResponse = ApiResponse.error("Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/closest")
    public ResponseEntity<List<FireDepartment>> getNearestFireDepartments(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        try {
            List<FireDepartment> nearestFireDepartments = fireDepartmentService.getNearestFireDepartments(latitude, longitude);
            return new ResponseEntity<>(nearestFireDepartments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

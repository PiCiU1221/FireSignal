package com.piciu1221.firesignal.controller;

import com.piciu1221.firesignal.dto.FirefighterAddDTO;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.service.FirefighterService;
import com.piciu1221.firesignal.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firefighters")
public class FirefighterController {

    private final FirefighterService firefighterService;

    @Autowired
    public FirefighterController(FirefighterService firefighterService) {
        this.firefighterService = firefighterService;
    }

    /**
     * Add a new firefighter.
     *
     * @param addDTO The DTO containing information for adding the firefighter.
     * @return ResponseEntity with an ApiResponse containing the result message and HTTP status.
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addFirefighter(@RequestBody FirefighterAddDTO addDTO) {
        try {
            // Call the service method to add a firefighter
            ApiResponse<String> response = firefighterService.addFirefighter(addDTO);

            // Determine HTTP status based on the success flag in the response
            HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;

            // Return ResponseEntity with the ApiResponse and appropriate HTTP status
            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an ApiResponse with an error message
            ApiResponse<String> errorResponse = ApiResponse.error("Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a paginated list of firefighters.
     *
     * @param page The page number for pagination (default is 0).
     * @return ResponseEntity containing the list of firefighters and HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<Firefighter>> getFirefighters(@RequestParam(defaultValue = "0") int page) {
        // Implement pagination logic here using the page parameter
        List<Firefighter> firefighters = firefighterService.getFirefightersByPage(page);
        return ResponseEntity.ok(firefighters);
    }

    /**
     * Get a paginated list of firefighters by username.
     *
     * @param page     The page number for pagination (default is 0).
     * @param username The username for filtering firefighters.
     * @return ResponseEntity containing the list of firefighters and HTTP status.
     */
    @GetMapping("/firefighters-by-username")
    public ResponseEntity<List<Firefighter>> getFirefightersByUsername(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = true) String username) {
        List<Firefighter> firefighters = firefighterService.getFirefightersByUsernameAndPage(page, username);
        return ResponseEntity.ok(firefighters);
    }

    /**
     * Check if a firefighter has a department assigned.
     *
     * @param username The username of the firefighter.
     * @return ResponseEntity with an ApiResponse containing the result message and HTTP status.
     */
    @GetMapping("/has-department")
    public ResponseEntity<ApiResponse<Boolean>> hasDepartmentAssigned(@RequestParam String username) {
        try {
            boolean hasDepartment = firefighterService.hasDepartmentAssigned(username);
            return ResponseEntity.ok(ApiResponse.success("Department check successful", hasDepartment));
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            ApiResponse<Boolean> errorResponse = ApiResponse.error("Error checking department: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Add a new firefighter with a specified username.
     *
     * @param addDTO   The DTO containing information for adding the firefighter.
     * @param username The username to associate with the firefighter.
     * @return ResponseEntity with an ApiResponse containing the result message and HTTP status.
     */
    @PostMapping("/add-with-username")
    public ResponseEntity<ApiResponse<String>> addFirefighterWithUsername(@RequestBody FirefighterAddDTO addDTO,
                                                                          @RequestParam String username) {
        try {
            // Call the service method to add a firefighter
            ApiResponse<String> response = firefighterService.addFirefighterWithUsername(addDTO, username);

            // Determine HTTP status based on the success flag in the response
            HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;

            // Return ResponseEntity with the ApiResponse and appropriate HTTP status
            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an ApiResponse with an error message
            ApiResponse<String> errorResponse = ApiResponse.error("Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Delete a firefighter by username.
     *
     * @param username The username of the firefighter to be deleted.
     * @return ResponseEntity with an ApiResponse containing the result message and HTTP status.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse<String>> deleteFirefighter(@PathVariable String username) {
        try {
            // Call the service method to delete a firefighter by username
            ApiResponse<String> response = firefighterService.deleteFirefighterByUsername(username);

            // Determine HTTP status based on the success flag in the response
            HttpStatus httpStatus = response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

            // Return ResponseEntity with the ApiResponse and appropriate HTTP status
            return ResponseEntity.status(httpStatus).body(response);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an ApiResponse with an error message
            ApiResponse<String> errorResponse = ApiResponse.error("Error processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

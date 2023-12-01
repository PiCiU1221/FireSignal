package com.piciu1221.firesignal.dto;

import com.piciu1221.firesignal.entity.FireDepartment;
import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.repository.FireDepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class FirefighterAddDTO {

    private Long departmentId;
    private String firefighterName;
    private String firefighterUsername;
    private boolean firefighterCommander;
    private boolean firefighterDriver;
    private boolean firefighterTechnicalRescue;

    public FirefighterAddDTO() {

    }

    public Firefighter createFirefighterEntity(FireDepartmentRepository fireDepartmentRepository) {
        Firefighter firefighter = new Firefighter();

        // Retrieve FireDepartment from the database based on departmentId
        FireDepartment fireDepartment = fireDepartmentRepository.findById(departmentId).orElse(null);

        firefighter.setDepartmentId(fireDepartment);
        firefighter.setFirefighterName(firefighterName);
        firefighter.setFirefighterUsername(firefighterUsername);
        firefighter.setFirefighterCommander(firefighterCommander);
        firefighter.setFirefighterDriver(firefighterDriver);
        firefighter.setFirefighterTechnicalRescue(firefighterTechnicalRescue);

        return firefighter;
    }

    public Firefighter createFirefighterEntity(FireDepartmentRepository fireDepartmentRepository,
                                               String username) {
        Firefighter firefighter = new Firefighter();

        // Retrieve FireDepartment from the database based on the username
        FireDepartment fireDepartment = fireDepartmentRepository.findByFirefighterUsername(username);

        // Set the retrieved FireDepartment to the Firefighter
        firefighter.setFireDepartment(fireDepartment);

        firefighter.setFirefighterName(firefighterName);
        firefighter.setFirefighterUsername(firefighterUsername);
        firefighter.setFirefighterCommander(firefighterCommander);
        firefighter.setFirefighterDriver(firefighterDriver);
        firefighter.setFirefighterTechnicalRescue(firefighterTechnicalRescue);

        return firefighter;
    }
}
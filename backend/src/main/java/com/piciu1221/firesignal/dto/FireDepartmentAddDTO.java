package com.piciu1221.firesignal.dto;

import com.piciu1221.firesignal.entity.FireDepartment;
import com.piciu1221.firesignal.entity.Firefighter;
import lombok.Data;

@Data
public class FireDepartmentAddDTO {
    private String chiefFirstName;
    private String chiefSecondName;
    private String chiefUsername;
    private String departmentCity;
    private String departmentLatitude;
    private String departmentLongitude;
    private String departmentName;
    private String departmentStreet;
    private boolean isCommander;
    private boolean isDriver;
    private boolean isTechnicalRescue;

    public FireDepartment createFireDepartmentEntity() {
        FireDepartment fireDepartment = new FireDepartment();
        fireDepartment.setDepartmentName(this.departmentName);
        fireDepartment.setDepartmentCity(this.departmentCity);
        fireDepartment.setDepartmentStreet(this.departmentStreet);
        fireDepartment.setDepartmentLatitude(Double.parseDouble(this.departmentLatitude));
        fireDepartment.setDepartmentLongitude(Double.parseDouble(this.departmentLongitude));

        return fireDepartment;
    }

    public Firefighter createFirefighterEntity() {
        Firefighter firefighter = new Firefighter();
        firefighter.setFirefighterName(this.chiefFirstName + " " + this.chiefSecondName);
        firefighter.setFirefighterUsername(this.chiefUsername);
        firefighter.setFirefighterCommander(this.isCommander);
        firefighter.setFirefighterDriver(this.isDriver);
        firefighter.setFirefighterTechnicalRescue(this.isTechnicalRescue);

        return firefighter;
    }
}

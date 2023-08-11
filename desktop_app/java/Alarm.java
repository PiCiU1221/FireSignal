package main.java;

import java.time.LocalDateTime;

public class Alarm {
    private int alarmId;
    private String city;
    private String street;
    private String description;
    private LocalDateTime dateTime;

    // Constructors
    public Alarm(int alarmId, String city, String street, String description, LocalDateTime dateTime) {
        this.alarmId = alarmId;
        this.city = city;
        this.street = street;
        this.description = description;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setAddress(String address) {
        this.street = street;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

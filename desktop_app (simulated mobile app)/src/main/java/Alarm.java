package main.java;

import java.time.LocalDateTime;

public class Alarm {
    private int alarmId;
    private String city;
    private String street;
    private String description;
    private LocalDateTime dateTime;
    private int count;
    private boolean hasAcceptedCommander;
    private int acceptedDriversCount;
    private int acceptedFirefightersCount;
    private boolean hasAcceptedTechnicalRescue;

    // Constructors
    public Alarm(int alarmId, String city, String street, String description, LocalDateTime dateTime,
                 int count, boolean hasAcceptedCommander, int acceptedDriversCount,
                 int acceptedFirefightersCount, boolean hasAcceptedTechnicalRescue) {
        this.alarmId = alarmId;
        this.city = city;
        this.street = street;
        this.description = description;
        this.dateTime = dateTime;
        this.count = count;
        this.hasAcceptedCommander = hasAcceptedCommander;
        this.acceptedDriversCount = acceptedDriversCount;
        this.acceptedFirefightersCount = acceptedFirefightersCount;
        this.hasAcceptedTechnicalRescue = hasAcceptedTechnicalRescue;
    }

    public Alarm(int count, boolean hasAcceptedCommander, int acceptedDriversCount,
                 int acceptedFirefightersCount, boolean hasAcceptedTechnicalRescue) {
        this.count = count;
        this.hasAcceptedCommander = hasAcceptedCommander;
        this.acceptedDriversCount = acceptedDriversCount;
        this.acceptedFirefightersCount = acceptedFirefightersCount;
        this.hasAcceptedTechnicalRescue = hasAcceptedTechnicalRescue;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasAcceptedCommander() {
        return hasAcceptedCommander;
    }

    public void setHasAcceptedCommander(boolean hasAcceptedCommander) {
        this.hasAcceptedCommander = hasAcceptedCommander;
    }

    public int getAcceptedDriversCount() {
        return acceptedDriversCount;
    }

    public void setAcceptedDriversCount(int acceptedDriversCount) {
        this.acceptedDriversCount = acceptedDriversCount;
    }

    public int getAcceptedFirefightersCount() {
        return acceptedFirefightersCount;
    }

    public void setAcceptedFirefightersCount(int acceptedFirefightersCount) {
        this.acceptedFirefightersCount = acceptedFirefightersCount;
    }

    public boolean isHasAcceptedTechnicalRescue() {
        return hasAcceptedTechnicalRescue;
    }

    public void setHasAcceptedTechnicalRescue(boolean hasAcceptedTechnicalRescue) {
        this.hasAcceptedTechnicalRescue = hasAcceptedTechnicalRescue;
    }
}

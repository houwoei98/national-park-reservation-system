package com.techelevator.model;

import java.time.LocalDate;

public class Park {
    private long parkId;
    private String parkName;
    private String location;
    private LocalDate establishedDate;
    private int area;
    private int visitors;
    private String description;

    public Park() {
    }

    public Park(long parkId, String parkName, String location, LocalDate establishedDate, int area, int visitors, String description) {
        this.parkId = parkId;
        this.parkName = parkName;
        this.location = location;
        this.establishedDate = establishedDate;
        this.area = area;
        this.visitors = visitors;
        this.description = description;
    }

    public long getParkId() {
        return parkId;
    }

    public void setParkId(long parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(LocalDate establishedDate) {
        this.establishedDate = establishedDate;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

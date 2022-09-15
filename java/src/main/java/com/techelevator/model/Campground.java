package com.techelevator.model;

import java.time.LocalDate;

public class Campground {

    private long campgroundId;
    private long parkId;
    private String campgroundName;
    private String openFrom;
    private String openTo;
    private String dailyFee;

    public Campground() {
    }

    public Campground(long campgroundId, long parkId, String campgroundName, String openFrom, String openTo, String dailyFee) {
        this.campgroundId = campgroundId;
        this.parkId = parkId;
        this.campgroundName = campgroundName;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.dailyFee = dailyFee;
    }

    public long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public long getParkId() {
        return parkId;
    }

    public void setParkId(long parkId) {
        this.parkId = parkId;
    }

    public String getCampgroundName() {
        return campgroundName;
    }

    public void setCampgroundName(String campgroundName) {
        this.campgroundName = campgroundName;
    }

    public String getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(String openFrom) {
        this.openFrom = openFrom;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    public String getDailyFee() {
        return dailyFee;
    }

    public void setDailyFee(String dailyFee) {
        this.dailyFee = dailyFee;
    }
}

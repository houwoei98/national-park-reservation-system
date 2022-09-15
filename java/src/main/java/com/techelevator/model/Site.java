package com.techelevator.model;

public class Site {
    private long siteId;
    private long campgroundId;
    private int siteNumber;
    private int maxOccupancy;
    private boolean isAccessible;
    private double maxRVLength;
    private boolean isUtilities;
    private double cost;
    private String campgroundName;

    public String getCampgroundName() {
        return campgroundName;
    }

    public void setCampgroundName(String campgroundName) {
        this.campgroundName = campgroundName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Site() {
    }

    public Site(long siteId, long campgroundId, int siteNumber, int maxOccupancy, boolean isAccessible, double maxRVLength, boolean isUtilities) {
        this.siteId = siteId;
        this.campgroundId = campgroundId;
        this.siteNumber = siteNumber;
        this.maxOccupancy = maxOccupancy;
        this.isAccessible = isAccessible;
        this.maxRVLength = maxRVLength;
        this.isUtilities = isUtilities;
    }

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getCampgroundId() {
        return campgroundId;
    }

    public void setCampgroundId(long campgroundId) {
        this.campgroundId = campgroundId;
    }

    public int getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(int siteNumber) {
        this.siteNumber = siteNumber;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public double getMaxRVLength() {
        return maxRVLength;
    }

    public void setMaxRVLength(double maxRVLength) {
        this.maxRVLength = maxRVLength;
    }

    public boolean isUtilities() {
        return isUtilities;
    }

    public void setUtilities(boolean utilities) {
        isUtilities = utilities;
    }
}

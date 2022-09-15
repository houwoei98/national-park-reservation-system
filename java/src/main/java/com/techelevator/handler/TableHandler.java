package com.techelevator.handler;

import com.techelevator.dao.CampgroundDao;
import com.techelevator.dao.ParkDao;
import com.techelevator.dao.ReservationDao;
import com.techelevator.dao.SiteDao;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

import java.time.LocalDate;
import java.util.List;

public class TableHandler implements TableManagement{

    private CampgroundDao campgroundDao;
    private ParkDao parkDao;
    private ReservationDao reservationDao;
    private SiteDao siteDao;

    public TableHandler(CampgroundDao campgroundDao, ParkDao parkDao, ReservationDao reservationDao, SiteDao siteDao) {
        this.campgroundDao = campgroundDao;
        this.parkDao = parkDao;
        this.reservationDao = reservationDao;
        this.siteDao = siteDao;
    }

    @Override
    public List<Site> loadSiteFromCriteria(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate) {
        return siteDao.loadSiteFromCriteria(park, campground,arrivalDate,departureDate);
    }

    @Override
    public Site getSiteFromCampgroundAndSiteNumber(Campground campground, int siteNumber, Park park) {
        return siteDao.getSiteFromCampgroundAndSiteNumber(campground,siteNumber,park);
    }

    @Override
    public List<Site> getAllSites(LocalDate arrivalDate, LocalDate departureDate, Park park) {
        return siteDao.getAllSites(arrivalDate,departureDate, park);
    }

    @Override
    public List<Site> loadSiteFromCriteriaAdvanced(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate, int maxOccupancy, boolean wheelchairAccessible, double rvLength, boolean utilities) {
        return siteDao.loadSiteFromCriteriaAdvanced(park, campground,arrivalDate,departureDate,maxOccupancy,wheelchairAccessible,rvLength,utilities);
    }

    @Override
    public Reservation addReservation(Site site, String name, LocalDate fromDate, LocalDate toDate) {
        return reservationDao.addReservation(site,name,fromDate,toDate);
    }

    @Override
    public List<Reservation> getUpcomingReservations(Park park) {
        return reservationDao.getUpcomingReservations(park);
    }


    @Override
    public List<Park> LoadAllPark() {
        return parkDao.LoadAllPark();
    }

    @Override
    public List<Park> LoadAllParksFromName(String parkName) {
        return parkDao.LoadAllParksFromName(parkName);
    }

    @Override
    public List<Campground> LoadCampground(long campgroundId) {
        return campgroundDao.LoadCampground(campgroundId);
    }

    @Override
    public List<Campground> LoadAllCampgroundsInPark(String parkName) {
        return campgroundDao.LoadAllCampgroundsInPark(parkName);
    }
}

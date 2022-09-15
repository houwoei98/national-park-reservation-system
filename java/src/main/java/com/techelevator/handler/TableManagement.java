package com.techelevator.handler;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.List;

public interface TableManagement {
    List<Site> loadSiteFromCriteria(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate);
    Site getSiteFromCampgroundAndSiteNumber(Campground campground, int siteNumber, Park park);
    List<Site> getAllSites (LocalDate arrivalDate, LocalDate departureDate, Park park);
    List<Site> loadSiteFromCriteriaAdvanced(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate, int maxOccupancy, boolean wheelchairAccessible, double rvLength, boolean utilities) ;
    Reservation addReservation(Site site, String name, LocalDate fromDate, LocalDate toDate);
    List<Reservation> getUpcomingReservations (Park park);
    List<Park> LoadAllPark();
    List<Park> LoadAllParksFromName(String parkName);
    List<Campground> LoadCampground(long campgroundId);
    List<Campground> LoadAllCampgroundsInPark(String parkName);

}

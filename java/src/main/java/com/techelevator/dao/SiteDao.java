package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.time.LocalDate;
import java.util.List;

public interface SiteDao {
    List<Site> loadSiteFromCriteria(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate);
    Site getSiteFromCampgroundAndSiteNumber(Campground campground, int siteNumber, Park park);
    List<Site> getAllSites (LocalDate arrivalDate, LocalDate departureDate, Park park);
    List<Site> loadSiteFromCriteriaAdvanced(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate, int maxOccupancy, boolean wheelchairAccessible, double rvLength, boolean utilities) ;

    }

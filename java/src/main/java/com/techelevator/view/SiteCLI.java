package com.techelevator.view;

import com.techelevator.handler.TableManagement;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SiteCLI {

    private PrintStream out;
    private InputStream userInput;
    private Menu menu;
    private TableManagement tableHandler;

    public SiteCLI (PrintStream out, InputStream in, Menu menu, TableManagement tableHandler) {
        this.out = out;
        this.userInput = in;
        this.menu = menu;
        this.tableHandler = tableHandler;
    }

    public List<Site> displayAllSites(LocalDate arrivalDate, LocalDate departuredate, Park park) {
        List<Site> sites = new ArrayList<>();
        String output = "No.  Campground      Site No.  Max Occup.  Accessible?  Max RV Length  Utility    Cost";
        out.println(output);
//        for (Site site: tableHandler.getAllSites(arrivalDate, departuredate, park)) {
//            displaySiteDetail(site);
//            sites.add(site);
//        }
        for (int i = 1; i <= tableHandler.getAllSites(arrivalDate,departuredate,park).size(); i++) {
            Site site = tableHandler.getAllSites(arrivalDate,departuredate,park).get(i - 1);
            displaySiteDetail(site, i);
            sites.add(site);
        }

        return sites;


    }

    public List<Integer> displaySitesByCampground(Park park, Campground campground,LocalDate arrivalDate, LocalDate departureDate) {
        List<Integer> siteNumberLists = new ArrayList<>();
        String output = "Site No.  Max Occup.  Accessible?  Max RV Length  Utility    Cost";
        out.println(output);
        for (Site site: tableHandler.loadSiteFromCriteria(park, campground, arrivalDate, departureDate)) {
            displaySiteDetailByCampground(site);
            siteNumberLists.add(site.getSiteNumber());
        }
        return siteNumberLists;
    }

    private void displaySiteDetail(Site site, int i) {
        String output =  " "+ i + "   " +
                site.getCampgroundName() +
                "        " +
                String.format("%02d", site.getSiteNumber()) +
                "  " +
                "       " +
                site.getMaxOccupancy() +
                "            " +
                        (site.isAccessible() ? "Yes": "No") +
                "           " +
                        (site.getMaxRVLength() == 0 ? "N/A" : site.getMaxRVLength()) +
                "         " +
                        (site.isUtilities() ? "Yes": "N/A") +
                "      $" +
                String.format("%.2f", site.getCost());
        out.println(output);

    }

    private void displaySiteDetailByCampground(Site site) {
        String output = "   " +
                        String.format("%02d", site.getSiteNumber()) +
                        "  " +
                        "       " +
                        site.getMaxOccupancy() +
                        "            " +
                        (site.isAccessible() ? "Yes": "No") +
                        "           " +
                        (site.getMaxRVLength() == 0 ? "N/A" : site.getMaxRVLength()) +
                        "         " +
                        (site.isUtilities() ? "Yes": "No") +
                        "      $" +
                        String.format("%.2f", site.getCost());
        out.println(output);

    }


}

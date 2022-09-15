package com.techelevator.view;

import com.techelevator.handler.TableManagement;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ApplicationCLI {
    private Menu menu;
    //    private MenuManagement menuHandler;
    private TableManagement tableHandler;
    private PrintStream out;
    private Scanner in;
    private ParkCLI parkCli;
    private CampgroundCLI campgroundCli;
    private SiteCLI siteCli;
    private ReservationCLI reservationCli;

    public ApplicationCLI(Menu menu, TableManagement tableHandler, PrintStream out, InputStream in, ParkCLI parkCli, CampgroundCLI campgroundCli, SiteCLI siteCli, ReservationCLI reservationCli) {
        this.menu = menu;
        this.tableHandler = tableHandler;
        this.out = out;
        this.in = new Scanner(in);
        this.parkCli = parkCli;
        this.campgroundCli = campgroundCli;
        this.siteCli = siteCli;
        this.reservationCli = reservationCli;
    }

    public void run() {
        showMenu();
    }

    private void showMenu() {
        initialDisplay();
        boolean exitingChoice = false;
        while(!exitingChoice) {

            out.println();
            out.println();
            out.println("PLEASE SELECT A PARK");
            String choice = (String) menu.getChoiceFromOptions(new String[]{

                   // parkCli.displayAllParks();
                    "Acadia",
                    "Arches",
                    "Cuyahoga Valley",
                    "Exit"
            }, null);

            switch (choice) {
                case "Exit":
                    exitingChoice = true;
                    break;
                default:
                    parkCli.displayParkFromName(choice);

                    boolean exitingChoiceCampground = false;
                    while (!exitingChoiceCampground) {
                        String choiceCampground = (String) menu.getChoiceFromOptions(new String[]{
                                "View Campgrounds",
                                "Search for Reservation",
                                "Reservation for next 30 days",
                                "Return to Previous Screen"
                        }, null);

                        List<Park> selectedParks = tableHandler.LoadAllParksFromName(choice);

                        Park selectedPark = selectedParks.get(0);

                        switch (choiceCampground) {
                            case "View Campgrounds":
                               campgroundCli.displayAllCampgrounds(choice);

                                String choiceReservation = (String) menu.getChoiceFromOptions(new String[]{
                                        "Search for Available Reservation",
                                        "Return to Previous Screen"

                                }, null);
                                switch (choiceReservation) {
                                    case "Search for Available Reservation":
                                        campgroundCli.displayAllCampgrounds(choice);
                                        // prompt user for which campground, arrival date and departure date
                                        out.println("\nWhich campground (enter 0 to cancel)?");
                                        String campgroundNumber = in.nextLine();
                                        if(campgroundNumber.equals("0")) { break;}

                                        boolean availableSiteBool = false;
                                        List<Integer> siteNumberList = new ArrayList<>();
                                        String arrivalDate = "";
                                        String departureDate = "";
                                        Campground selectedCampground = new Campground();
                                        while (!availableSiteBool) {
                                            arrivalDate = checkArrivalDate();
                                            departureDate = checkDepartureDate();

                                            List<Campground> selectedCampgrounds = tableHandler.LoadCampground(Integer.valueOf(campgroundNumber));
                                            selectedCampground = selectedCampgrounds.get(0);
                                            out.println("\n                        Site Availability");
                                            out.println("--------------------------------------------------------------------");

                                            siteNumberList = siteCli.displaySitesByCampground(selectedPark, selectedCampground,  LocalDate.parse(arrivalDate), LocalDate.parse(departureDate));
                                            if (siteNumberList.isEmpty()) {
                                                out.println("\n There are no available sites during the specified date range. Please enter an alternative date range");
                                            } else {
                                                availableSiteBool = true;
                                            }
                                        }


                                        boolean siteNumberExit = false;
                                        String siteNumber = "";
                                        while (!siteNumberExit) {
                                            out.println("\nWhich site should be reserved (enter 0 to cancel)?");
                                            siteNumber = in.nextLine();
                                            if (siteNumberList.contains(Integer.valueOf(siteNumber))) {
                                                siteNumberExit = true;
                                            } else if (siteNumber.equals("0")) {
                                                break;
                                            }
                                            else{
                                                out.println("\nYou have inputted the wrong site number for this location. Please try again");
                                            }
                                        }

                                        if(siteNumber.equals("0")) { break;}
                                        out.println("\nWhat name should the reservation be made under?");
                                        String reservationName = in.nextLine();

                                        Site selectedSite = tableHandler.getSiteFromCampgroundAndSiteNumber(selectedCampground, Integer.valueOf(siteNumber), selectedPark);

                                        reservationCli.insertReservation(selectedSite, reservationName, LocalDate.parse(arrivalDate), LocalDate.parse(departureDate));


                                        break;
                                    case "Return to Previous Screen":
                                        break;
                                }

                                break;

                            case "Search for Reservation":



                                boolean availableSiteBool = false;
                                List<Integer> siteNumberList = new ArrayList<>();
                                String arrivalDate = "";
                                String departureDate = "";
                                List<Site> sites = new ArrayList<>();
                                while (!availableSiteBool) {
                                    arrivalDate = checkArrivalDate();
                                    departureDate = checkDepartureDate();

                                    out.println("\n                                " +choice + " Site Availability");
                                    out.println("-----------------------------------------------------------------------------------------");
                                    sites = siteCli.displayAllSites(LocalDate.parse(arrivalDate), LocalDate.parse(departureDate), selectedPark);

                                    if (sites.isEmpty()) {
                                        out.println("\n There are no available sites during the specified date range. Please enter an alternative date range");
                                    } else {
                                        availableSiteBool = true;
                                    }
                                }

                                boolean siteNumberExit = false;
                                String siteNumber = "";
                                while (!siteNumberExit) {
                                    out.println("\nWhich site should be reserved (enter 0 to cancel)?");
                                    siteNumber = in.nextLine();
                                    if (Integer.parseInt(siteNumber) >= 1 && Integer.parseInt(siteNumber) <= 5) {
                                        siteNumberExit = true;
                                    } else if (siteNumber.equals("0")) {
                                        break;
                                    } else {
                                        out.println("\nYou have inputted the wrong number for this location. Please try again");
                                    }
                                }

                                if(siteNumber.equals("0")) {break;}
                                out.println("\nWhat name should the reservation be made under?");
                                String reservationName = in.nextLine();

                                Long seletedCampgroundId = sites.get(Integer.valueOf(siteNumber) - 1).getCampgroundId();
                                List<Campground> selectedCampgroundParkWideList = tableHandler.LoadCampground(seletedCampgroundId);
                                Campground selectedCampgroundParkWide = selectedCampgroundParkWideList.get(0);

                                Site selectedSite = tableHandler.getSiteFromCampgroundAndSiteNumber(selectedCampgroundParkWide, sites.get(Integer.valueOf(siteNumber) - 1).getSiteNumber(), selectedPark);

                                reservationCli.insertReservation(selectedSite, reservationName, LocalDate.parse(arrivalDate), LocalDate.parse(departureDate));

                                break;
                            case "Reservation for next 30 days":
                                out.println("                                                      Reservation for Next 30 Days");
                                out.println("-----------------------------------------------------------------------------------------------------------------------------");
                                reservationCli.displayReservationNext30Days(selectedPark);
                                break;
                            case "Return to Previous Screen":
                                exitingChoiceCampground = true;
                                break;
                        }
                    }
            }
        }

    }

    public String checkArrivalDate() {
        boolean arrivalDateExit = false;
        String arrivalDate = "";
        while (!arrivalDateExit) {
            out.println("\nWhat is the arrival date? (Please enter in this format: yyyy-MM-dd)");
            arrivalDate = in.nextLine();
            if (isValidFormat("yyyy-MM-dd", arrivalDate, Locale.ENGLISH)) {
                arrivalDateExit = true;
            } else {
                out.println("\nYou have inputted the wrong format. Please try again.");
            }
        }
        return arrivalDate;
    }

    public String checkDepartureDate() {
        boolean departureDateExit = false;
        String departureDate = "";
        while (!departureDateExit) {
            out.println("\nWhat is the departure date? (Please enter in this format: yyyy-MM-dd)");
            departureDate = in.nextLine();
            if (isValidFormat("yyyy-MM-dd", departureDate, Locale.ENGLISH)) {
                departureDateExit = true;
            } else {
                out.println("\nYou have inputted the wrong format. Please try again.");
            }
        }
        return departureDate;
    }

    public static boolean isValidFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

        try {
            ldt = LocalDateTime.parse(value, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, fomatter);
                String result = ld.format(fomatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    // Debugging purposes
                    //e2.printStackTrace();
                }
            }
        }

        return false;
    }

    public void initialDisplay() {
        String output = "         %%%%%%%%%%%%%%%%%%      %%%%%%%%%%%%%%%%%%                 %%%                    %%%         %%%          %%%     %%%\n" +
                        "         %%%%%%%%%%%%%%%%%%      %%%%%%%%%%%%%%%%%%              %%%   %%%               %%% %%%     %%% %%%        %%%     %%%\n" +
                        "                 %%%             %%%                            %%%     %%%              %%%  %%%   %%%  %%%        %%%     %%%\n" +
                        "                 %%%             %%%                           %%%       %%%             %%%   %%% %%%   %%%        %%%     %%%\n" +
                        "                 %%%             %%%%%%%%%%%%%%%              %%%%%%%%%%%%%%%            %%%     %%%     %%%        %%%     %%%\n" +
                        "                 %%%             %%%%%%%%%%%%%%%             %%%%%%%%%%%%%%%%%           %%%             %%%        %%%     %%%\n" +
                        "                 %%%             %%%                        %%%             %%%          %%%             %%%        %%%     %%%\n" +
                        "                 %%%             %%%                       %%%               %%%         %%%             %%%        %%%     %%%\n" +
                        "                 %%%             %%%%%%%%%%%%%%%%%%       %%%                 %%%        %%%             %%%        %%%     %%%\n" +
                        "                 %%%             %%%%%%%%%%%%%%%%%%      %%%                   %%%       %%%             %%%        %%%     %%%;";

        String output1 = "\n\n\n\n\n                                             _____                                                      _\n" +
                "                                            / ____|                                                    | |\n" +
                "                                           | |     __ _ _ __ ___  _ __   __ _ _ __ ___  _   _ _ __   __| |\n" +
                "                                           | |    / _` | '_ ` _ \\| '_ \\ / _` | '__/ _ \\| | | | '_ \\ / _` |\n" +
                "                                           | |___| (_| | | | | | | |_) | (_| | | | (_) | |_| | | | | (_| |\n" +
                "                                            \\_____\\__,_|_| |_| |_| .__/ \\__, |_|  \\___/ \\__,_|_| |_|\\__,_|\n" +
                "                                                                 | |     __/ |\n" +
                "                                                                 |_|    |___/";

        String output2 = "\n\n\n                                                                       In a\n" +
                            "                                                                    Galaxy far \n" +
                            "                                                                   far away Team \n" +
                            "                                                                  2 were trained by \n" +
                            "                                                                 Rob Stewart and Kris \n" +
                            "                                                               Jackson. The new trained \n" +
                            "                                                             Java Jedi Christopher,Kirsty,\n" +
                            "                                                            Lim,Myles and Shilpa Have faced\n" +
                            "                                                          many battles along their journey The\n" +
                            "                                                         war in intelliJ has been raging on for 6\n" +
                            "                                                        weeks now with distinct battles such as Big\n" +
                            "                                                      Decimal, Interface, Abstract and Loops and Arrays\n" +
                            "                                                     watch as their Application comes to life ............";

        List<String> introStrings = new ArrayList<>();
        introStrings.add(AnsiColors.yellowValue(output,true));
        introStrings.add(AnsiColors.yellowValue(output1,true));
        introStrings.add(AnsiColors.yellowValue("\n\n\n                                                                       In a\n",true));
        introStrings.add(AnsiColors.yellowValue("                                                                    Galaxy far \n",true));
        introStrings.add(AnsiColors.yellowValue("                                                                   far away Team \n", true));
        introStrings.add(AnsiColors.yellowValue("                                                                  2 were trained by \n", true));
        introStrings.add(AnsiColors.yellowValue("                                                                 Rob Stewart and Kris \n", true));
        introStrings.add(AnsiColors.yellowValue("                                                               Jackson. The new trained \n", true));
        introStrings.add(AnsiColors.yellowValue("                                                             Java Jedi Christopher,Kirsty,\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                            Lim,Myles and Shilpa have faced\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                          many battles along their journey. The\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                         war in intelliJ has been raging on for 6\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                        weeks now with distinct battles such as Big\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                      Decimal, Interface, Abstract and Loops and Arrays\n", true));
        introStrings.add(AnsiColors.yellowValue("                                                     watch as their Application comes to life ............", true));

        for (String introString: introStrings) {
            out.println(introString);
            try {
                Thread.sleep(1750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // SHOW ALL PARKS (Req 1)
    // Park Information Screen (Req 2)
    // 1) View Campgrounds
    // 1) Search for Available Reservation (Req 3 & 4)
    // UI for "Search for Campground Reservation"
    // 2) Return to Previous Screen
    // 2) Search for Reservation (Req 3 & 4)
    // UI for "Search for Campground Reservation"
    // 3) BONUS: Search for Park-wide Reservation (Req 5)
    // 4) BONUS: List of upcoming reservations for next 30 days of a selected national park (Req 6)
    // 5) Return to Previous Screen

}

package com.techelevator.view;

import com.techelevator.handler.TableManagement;
import com.techelevator.model.Campground;

import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CampgroundCLI {

    private Menu menu;
    private TableManagement tableHandler;
    private PrintStream out;
    private InputStream in;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CampgroundCLI(Menu menu, TableManagement tableHandler, PrintStream out, InputStream in) {
        this.menu = menu;
        this.tableHandler = tableHandler;
        this.out = out;
        this.in = in;
    }


    public void displayAllCampgrounds(String parkName) {
        System.out.format("%83s", parkName.toUpperCase(Locale.ROOT) + " CAMPGROUNDS");
        out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

        int count = 1;

        Map<String,String> months = new HashMap<String,String>();
        months.put("01", "January");
        months.put("02", "February");
        months.put("03", "March");
        months.put("04", "April");
        months.put("05", "May");
        months.put("06", "June");
        months.put("07", "July");
        months.put("08", "August");
        months.put("09", "September");
        months.put("10", "October");
        months.put("11", "November");
        months.put("12", "December");

        System.out.format("%-20s %-20s %-20s %-20s %20s",
                getPaddedString("Campground ID", ' ') ,
                getPaddedString("Campground Name", ' '),
                getPaddedString("Open From", ' '),
                getPaddedString("Open To", ' '),
                getPaddedString("Daily Fee", ' '));
        out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Campground campground : tableHandler.LoadAllCampgroundsInPark(parkName)) {

           System.out.format("%-20s %-20s %-20s %-20s %20s",
                   getPaddedString(String.valueOf(campground.getCampgroundId()), ' '),
                   getPaddedString(campground.getCampgroundName(), ' '),
                   getPaddedString(months.get(campground.getOpenFrom()), ' '),
                   getPaddedString(months.get(campground.getOpenTo()), ' '),
                   getPaddedString("£" + df.format(Double.parseDouble(campground.getDailyFee())), ' '));
            System.out.println();

            //count++;

        }
    }

    public static String getPaddedString(String str, char paddingChar) {
        if (str == null) {
            throw new NullPointerException("Can not add padding in null String!");
        }

        int maxPadding = 30;//This is what you have to decide
        int length = str.length();
        int padding = (maxPadding - length) / 2;//decide left and right padding
        if (padding <= 0) {
            return str;// return actual String if padding is less than or equal to 0
        }

        String empty = "", hash = "#";//hash is used as a place holder

        // extra character in case of String with even length
        int extra = (length % 2 == 0) ? 1 : 0;

        String leftPadding = "%" + padding + "s";
        String rightPadding = "%" + (padding - extra) + "s";

        String strFormat = leftPadding + "%s" + rightPadding;
        String formattedString = String.format(strFormat, empty, hash, empty);

        //Replace space with * and hash with provided String
        String paddedString = formattedString.replace(' ', paddingChar).replace(hash, str);
        return paddedString;
    }

}

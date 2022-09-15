package com.techelevator.view;

import com.techelevator.handler.TableManagement;
import com.techelevator.model.Campground;
import com.techelevator.model.Park;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

public class ParkCLI {

    private Menu menu;
    private TableManagement tableHandler;
    private PrintStream out;
    private InputStream in;

    public ParkCLI(Menu menu, TableManagement tableHandler, PrintStream out, InputStream in) {
        this.menu = menu;
        this.tableHandler = tableHandler;
        this.out = out;
        this.in = in;
    }

    public String[] displayAllParks() {
        String[] parks = new String[3];
        int count =0;

        for (Park park : tableHandler.LoadAllPark()) {
            // name
            parks[count]=park.getParkName();
            count++;
        } return parks;
    }


    public void displayParkFromName(String parkName) {

        String dash = "--------------------------------------------------------------------------------------------------------------------";

        out.println("");
        System.out.format("%64s", parkName.toUpperCase(Locale.ROOT) + " PARK \n" );
        System.out.println(dash);


        System.out.format("%-20s %-20s %-20s %-20s",
               getPaddedString("Park Location ", ' '),
               getPaddedString("Date Established", ' '),
                getPaddedString("Park Area", ' '),
               getPaddedString("Annual Visitors", ' '));
        out.println("");
        System.out.println(dash);



        for(Park park : tableHandler.LoadAllParksFromName(parkName)) {

           System.out.format("%-20s %-20s %-20s %-20s",
                   getPaddedString(park.getLocation(),' '),
                   getPaddedString(park.getEstablishedDate().toString(),' '),
                   getPaddedString(String.valueOf(park.getArea()),' '),
                   getPaddedString(String.valueOf(park.getVisitors()),' '));
           out.println("");
           out.println("");
           printDescription(park.getDescription());
           out.println("");

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

    public void printDescription(String parkDescription){
        String[] splits = parkDescription.split("\\.");

        for(int i=0; i <= splits.length -1; i++){
            if(splits[i].charAt(0) == ' '){
                out.println(splits[i].substring(1, splits[i].length()) + ". ");
            }else {
                out.println(splits[i] + ". ");
            }
        }
    }
}

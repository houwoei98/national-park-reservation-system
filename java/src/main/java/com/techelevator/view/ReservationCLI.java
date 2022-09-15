package com.techelevator.view;

import com.techelevator.handler.TableManagement;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationCLI {

    private PrintStream out;
    private InputStream userInput;
    private Menu menu;
    private TableManagement tableHandler;

    public ReservationCLI(PrintStream out, InputStream userInput, Menu menu, TableManagement tableHandler) {

        this.out = out;
        this.userInput = userInput;
        this.menu = menu;
        this.tableHandler = tableHandler;
    }

    public void insertReservation(Site site, String name, LocalDate fromDate, LocalDate toDate) {
        Reservation reservation = tableHandler.addReservation(site, name, fromDate, toDate);
        out.println("\nThe reservation has been made and the confirmation id is " + reservation.getReservationId());
    }

    public void displayReservationNext30Days (Park park) {
        String output = "Reservation Id.     Site Id.                  Name                        From Date            To Date            Create Date";
        out.println(output);
        List<Reservation> reservations = tableHandler.getUpcomingReservations(park);
        for (Reservation reservation : reservations) {
            displayReservationDetail(reservation);
        }
    }
    private void displayReservationDetail(Reservation reservation) {
        String output = "       " +
                String.format("%02d", reservation.getReservationId()) +
                        "             " +
                        String.format("%02d", reservation.getSiteId()) +
                        "  " +
                        "       " +
//                        fixedLengthString(reservation.getName(), 30) +
                getPaddedString(reservation.getName(), ' ')+
                        "            " +

                        reservation.getFromDate() +
                        "           " +
                        reservation.getToDate() +
                        "         " +
                        reservation.getCreateDate();
        out.println(output);

    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%1$"+length+ "s", string);
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

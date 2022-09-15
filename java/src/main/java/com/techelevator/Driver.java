package com.techelevator;

import javax.sql.DataSource;

import com.techelevator.dao.*;
import com.techelevator.handler.TableHandler;
import com.techelevator.handler.TableManagement;
import com.techelevator.view.*;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        CampgroundDao campgroundDao = new CampgroundDaoJdbc(dataSource);
        ParkDao parkDao = new ParkDaoJdbc(dataSource);
        SiteDao siteDao = new SiteDaoJdbc(dataSource);
        ReservationDao reservationDao = new ReservationDaoJdbc(dataSource);

        TableManagement tableManagement = new TableHandler(campgroundDao, parkDao, reservationDao, siteDao);

        InputStream userInput = System.in;
        PrintStream userOutput = System.out;

        Menu menu = new Menu(userInput, userOutput, true);
        ParkCLI parkCli = new ParkCLI(menu, tableManagement,  userOutput, userInput);
        CampgroundCLI campgroundCli = new CampgroundCLI(menu, tableManagement,  userOutput, userInput);
        SiteCLI siteCli = new SiteCLI(userOutput, userInput, menu, tableManagement);
        ReservationCLI reservationCli = new ReservationCLI(userOutput, userInput, menu, tableManagement);
        ApplicationCLI application = new ApplicationCLI(menu, tableManagement,  userOutput, userInput, parkCli, campgroundCli, siteCli, reservationCli);
        application.run();
    }

}



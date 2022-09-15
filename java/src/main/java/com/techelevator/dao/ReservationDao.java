package com.techelevator.dao;

import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDao {
    Reservation addReservation(Site site, String name, LocalDate fromDate, LocalDate toDate);
    List<Reservation> getUpcomingReservations (Park park);
}

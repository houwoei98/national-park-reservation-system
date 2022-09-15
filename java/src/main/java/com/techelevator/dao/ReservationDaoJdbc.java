package com.techelevator.dao;

import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDaoJdbc implements ReservationDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationDaoJdbc(DataSource datasource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    public Reservation getReservationById(Long reservationId) {
        String sql = "select reservation_id, site_id, name, from_date, to_date, create_date from reservation where reservation_id = :reservationId";
        Map<String, Object> params = new HashMap<>();
        params.put("reservationId", reservationId);
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, params);
        Reservation reservation = new Reservation();

        if (result.next()) {
            reservation = mapRowToReservation(result);
        }
        return reservation;
    }

    private Reservation mapRowToReservation(SqlRowSet results) {
        Reservation reservation = new Reservation();
        reservation.setReservationId(results.getLong("reservation_id"));
        reservation.setSiteId(results.getInt("site_id"));
        reservation.setName(results.getString("name"));
        reservation.setFromDate(results.getDate("from_date").toLocalDate());
        reservation.setToDate(results.getDate("to_date").toLocalDate());
        reservation.setCreateDate(results.getDate("create_date").toLocalDate());
        return reservation;
    }

    public Reservation addReservation(Site site, String name, LocalDate fromDate, LocalDate toDate) {
        String sql = "insert into reservation (site_id, name, from_date, to_date)\n" +
                "VALUES (:siteId, :name, :from_date, :to_date) returning reservation_id;";
        Map<String, Object> params = new HashMap<>();
        params.put("siteId", site.getSiteId());
        params.put("name", name);
        params.put("from_date", fromDate);
        params.put("to_date", toDate);

        Long reservationId = this.jdbcTemplate.queryForObject(sql, params, Long.class);
        return getReservationById(reservationId);
    }

    // Bonus Q6
    public List<Reservation> getUpcomingReservations (Park park) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "select reservation_id, s.site_id, reservation.name, from_date, to_date, create_date from reservation\n" +
                "    join site s on reservation.site_id = s.site_id\n" +
                "    join campground c on s.campground_id = c.campground_id\n" +
                "    join park p on c.park_id = p.park_id\n" +
                "where p.park_id = :park_id and from_date between current_date and (current_date + interval '30 days') and to_date between current_date and (current_date + interval '30 days');";
        Map<String, Object> params = new HashMap<>();
        params.put("park_id", park.getParkId());
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, params);

        while(results.next()) {
            reservations.add(mapRowToReservation(results));
        }
        return reservations;

    }
}

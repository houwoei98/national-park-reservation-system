package com.techelevator.dao;

import com.techelevator.model.Park;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkDaoJdbc implements ParkDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ParkDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Park> LoadAllPark() {

        String sql = "select\n" +
                "       park_id,\n" +
                "       name,\n" +
                "       location,\n" +
                "       establish_date,\n" +
                "       area,\n" +
                "       visitors,\n" +
                "       description \n" +
                "from park;";

        Map<String,Object> params = new HashMap<>();
        List<Park> parks = new ArrayList<Park>();

        SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(sql, params);

        while (rowset.next()) {
            Park park = new Park();
            park.setParkId(rowset.getLong("park_id"));
            park.setParkName(rowset.getString("name"));
            park.setLocation(rowset.getString("location"));
            park.setEstablishedDate((rowset.getDate("establish_date")).toLocalDate());
            park.setArea(rowset.getInt("area"));
            park.setVisitors(rowset.getInt("visitors"));
            park.setDescription(rowset.getString("description"));
            parks.add(park);
        }

        return parks;
    }


    @Override
    public List<Park> LoadAllParksFromName(String parkName) {
        String sql = "select park_id,\n" +
                "       location, \n" +
                "       establish_date,\n" +
                "       area,\n" +
                "       visitors,\n" +
                "       description\n" +
                "from park\n" +
                "where name = :parkName;";

        Map<String,Object> params = new HashMap<>();

        params.put("parkName",parkName);

        List<Park> parks = new ArrayList<Park>();

        SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(sql, params);

        while (rowset.next()) {
            Park park = new Park();
            park.setParkId(rowset.getLong("park_id"));
            park.setParkName(parkName);
            park.setLocation(rowset.getString("location"));
            park.setEstablishedDate((rowset.getDate("establish_date")).toLocalDate());
            park.setArea(rowset.getInt("area"));
            park.setVisitors(rowset.getInt("visitors"));
            park.setDescription(rowset.getString("description"));
            parks.add(park);
        }

        return parks;
    }

    @Override
    public Park mapRowToPark(SqlRowSet sqlResult) {
        return null;
    }


}

// Could we put everything inside the while loops in another method and call that method inside current methods?
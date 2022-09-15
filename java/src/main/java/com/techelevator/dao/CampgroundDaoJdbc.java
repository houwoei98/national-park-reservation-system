package com.techelevator.dao;

import com.techelevator.model.Campground;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampgroundDaoJdbc implements CampgroundDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CampgroundDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Campground> LoadCampground(long campgroundId) {

        String sql = "select \n" +
                "       park_id, \n" +
                "       name, \n" +
                "       open_from_mm, \n" +
                "       open_to_mm, \n" +
                "       daily_fee \n" +
                "from campground \n" +
                "where campground_id = :campgroundId;";

        Map<String,Object> params = new HashMap<>();
        params.put("campgroundId",campgroundId);

        List<Campground> Campgrounds = new ArrayList<Campground>();

        SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(sql, params);

        while (rowset.next()) {
            Campground Campground = new Campground();
            Campground.setCampgroundId(campgroundId);
            Campground.setParkId(rowset.getLong("park_id"));
            Campground.setCampgroundName(rowset.getString("name"));
            Campground.setOpenFrom(rowset.getString("open_from_mm"));
            Campground.setOpenTo(rowset.getString("open_to_mm"));
            Campground.setDailyFee(rowset.getBigDecimal("daily_fee").toString());
            Campgrounds.add(Campground);
        }

        return Campgrounds;

    }


    @Override
    public List<Campground> LoadAllCampgroundsInPark(String parkName) {

        String sql = "select \n" +
                "       c.park_id,\n" +
                "       c.campground_id, \n" +
                "       c.name, \n" +
                "       c.open_from_mm, \n" +
                "       c.open_to_mm, \n" +
                "       c.daily_fee \n" +
                "from campground c\n" +
                "    join park p on c.park_id = p.park_id\n" +
                "        where p.name = :parkName;";

        Map<String,Object> params = new HashMap<>();
        params.put("parkName",parkName);

        List<Campground> Campgrounds = new ArrayList<Campground>();

        SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(sql, params);

        while (rowset.next()) {

            Campground Campground = new Campground();
            Campground.setCampgroundId(rowset.getLong("campground_id"));
            Campground.setParkId(rowset.getLong("park_id"));
            Campground.setCampgroundName(rowset.getString("name"));
            Campground.setOpenFrom(rowset.getString("open_from_mm"));
            Campground.setOpenTo(rowset.getString("open_to_mm"));
            Campground.setDailyFee(rowset.getBigDecimal("daily_fee").toString());
            Campgrounds.add(Campground);
        }

        return Campgrounds;
    }


    @Override
    public Campground mapRowToCampground(SqlRowSet sqlResult) {
        return null;
    }
}

// Could we put everything inside the while loops in another method and call that method inside current methods?
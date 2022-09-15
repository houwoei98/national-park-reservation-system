package com.techelevator.dao;

import com.techelevator.model.Campground;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface CampgroundDao {

    List<Campground> LoadCampground(long campgroundId);
    List<Campground> LoadAllCampgroundsInPark(String parkName);
    Campground mapRowToCampground(SqlRowSet sqlResult);
}

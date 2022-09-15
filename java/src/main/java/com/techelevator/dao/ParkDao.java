package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ParkDao {

    List<Park> LoadAllPark();
    List<Park> LoadAllParksFromName(String parkName);
    Park mapRowToPark(SqlRowSet sqlResult);
}

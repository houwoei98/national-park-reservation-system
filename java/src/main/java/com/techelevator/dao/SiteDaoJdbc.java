package com.techelevator.dao;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.Site;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteDaoJdbc implements SiteDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public SiteDaoJdbc(DataSource datasource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    public List<Site> loadSiteFromCriteria(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate) {
        List<Site> sites = new ArrayList<>();

        String sql = "select distinct  c.campground_id as CampgroundId, c.name as CampgroundName, s.site_id, s.site_number,\n" +
                "                s.max_occupancy,\n" +
                "                s.accessible,\n" +
                "                s.max_rv_length,\n" +
                "                s.utilities,\n" +
                "                 c.daily_fee * (date_part('day', :endDate) - date_part('day',:startDate)) as Cost \n " +
                "from site s\n" +
                "         join campground c on s.campground_id = c.campground_id\n" +
                "         left join reservation r on s.site_id = r.site_id\n" +
                "            and ((:startDate between from_date and to_date)\n" +
                "            or (:endDate between from_date and to_date)\n" +
                "            or (\n" +
                "                                            (from_date between :startDate and :endDate)\n" +
                "                                            and (to_date between :startDate and :endDate)\n" +
                "                                        ))\n" +
                "\n" +
                "         join park p on c.park_id = p.park_id\n" +
                "where\n" +
                "    r.reservation_id is null and  p.park_id = :parkId and c.campground_id = :campgroundId LIMIT 5;";

        Map<String, Object> params = new HashMap<>();
        params.put("parkId", park.getParkId());
        params.put("campgroundId", campground.getCampgroundId());
        params.put("startDate", arrivalDate);
        params.put("endDate", departureDate);

        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, params);

        while (results.next()) {
            Site site = mapRowToSite(results);
            site.setCost(results.getDouble("Cost"));
            site.setCampgroundId(results.getLong("CampgroundId"));
            site.setSiteId(results.getLong("site_id"));
            site.setCampgroundName(results.getString("CampgroundName"));
            sites.add(site);
        }

        return sites;
    }

    private Site mapRowToSite(SqlRowSet results) {
        Site site = new Site();
        site.setSiteNumber(results.getInt("site_number"));
        site.setMaxOccupancy(results.getInt("max_occupancy"));
        site.setAccessible(results.getBoolean("accessible"));
        site.setMaxRVLength(results.getDouble("max_rv_length"));
        site.setUtilities(results.getBoolean("utilities"));
        return site;
    }

    public Site getSiteFromCampgroundAndSiteNumber(Campground campground, int siteNumber, Park park) {
        String sql = "select s.site_id, s.site_number,\n" +
                "       s.max_occupancy,\n" +
                "       s.accessible,\n" +
                "       s.max_rv_length,\n" +
                "       s.utilities\n" +
                "       from site s\n" +
                "    join campground c on s.campground_id = c.campground_id\n" +
                "    join park p on c.park_id = p.park_id\n" +
                "where site_number = :siteNumber and s.campground_id = :campgroundId and p.park_id = :parkId;";

        Map<String, Object> params = new HashMap<>();

        params.put("siteNumber", siteNumber);
        params.put("campgroundId", campground.getCampgroundId());
        params.put("parkId", park.getParkId());

        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, params);
        Site site = new Site();
        if(results.next()) {
            site = mapRowToSite(results);
            site.setSiteId(results.getLong("site_id"));
        }
        return site;
    }

    public List<Site> getAllSites (LocalDate arrivalDate, LocalDate departureDate, Park park) {
        List<Site> sites = new ArrayList<>();
        String sql1 = "select\n" +
                "c.campground_id as CampgroundId, c.name as CampgroundName,\n" +
                "       site.site_id,site.site_number,\n" +
                "       site.max_occupancy,\n" +
                "       site.accessible,\n" +
                "       site.max_rv_length,\n" +
                "       site.utilities," +
                "     c.daily_fee * (date_part('day', :endDate) - date_part('day',:startDate)) as Cost \n "+
                "from site\n" +
                "    join campground c on site.campground_id = c.campground_id\n" +
                "    left join reservation r on site.site_id = r.site_id\n" +
                "    join park p on c.park_id = p.park_id\n" +
                "where\n" +
                "                          c.park_id = :parkId and\n" +
                "                    ((from_date is null or to_date is null)) LIMIT 5;";

        String sql = "select distinct  c.campground_id as CampgroundId, c.name as CampgroundName, s.site_id, s.site_number,\n" +
                "                s.max_occupancy,\n" +
                "                s.accessible,\n" +
                "                s.max_rv_length,\n" +
                "                s.utilities,\n" +
                "                 c.daily_fee * (date_part('day', :endDate) - date_part('day',:startDate)) as Cost \n " +
                "from site s\n" +
                "         join campground c on s.campground_id = c.campground_id\n" +
                "         left join reservation r on s.site_id = r.site_id\n" +
                "            and ((:startDate between from_date and to_date)\n" +
                "            or (:endDate between from_date and to_date)\n" +
                "            or (\n" +
                "                                            (from_date between :startDate and :endDate)\n" +
                "                                            and (to_date between :startDate and :endDate)\n" +
                "                                        ))\n" +
                "\n" +
                "         join park p on c.park_id = p.park_id\n" +
                "where\n" +
                "    r.reservation_id is null and  p.park_id = :parkId LIMIT 5;";


        Map<String, Object> params = new HashMap<>();
        params.put("startDate", arrivalDate);
        params.put("endDate", departureDate);
        params.put("parkId", park.getParkId());
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, params);

        while(results.next()) {
            Site site = mapRowToSite(results);
            site.setCampgroundId(results.getLong("CampgroundId"));
            site.setSiteId(results.getLong("site_id"));
            site.setCampgroundName(results.getString("CampgroundName"));
            site.setCost(results.getDouble("Cost"));
            sites.add(site);
        }

        return sites;
    }

    //Bonus 3f --> NOT YET Bonus 3e
    public List<Site> loadSiteFromCriteriaAdvanced(Park park, Campground campground, LocalDate arrivalDate, LocalDate departureDate, int maxOccupancy, boolean wheelchairAccessible, double rvLength, boolean utilities) {
        List<Site> sites = new ArrayList<>();
        String sql = "select distinct s.site_number,\n" +
                "       s.max_occupancy,\n" +
                "       s.accessible,\n" +
                "       s.max_rv_length,\n" +
                "       s.utilities,\n" +
                "       c.daily_fee * (date_part('day', :departureDate) - date_part('day',:arrivalDate)) as Cost \n" +
                "       from site s\n" +
                "    join campground c on s.campground_id = c.campground_id join park p on c.park_id = p.park_id\n" +
                "    join reservation r on s.site_id = r.site_id\n" +
                "where p.park_id = :parkId and c.name = :campground\n" +
                "and s.max_occupancy >= :maxOccupancy and s.accessible = :wheelchairAccessible and s.max_rv_length = :rvLength and s.utilities = :utilities " +
                "and\n" +
                "    from_date is null or to_date is null or" +
                "    (:startDate not between from_date and to_date)\n" +
                "    and (:endDate not between from_date and to_date)\n" +
                "    and (\n" +
                "        (from_date not between :startDate and :endDate)\n" +
                "            and (to_date not between :startDate and :endDate)\n" +
                "        ) LIMIT 5;";
        Map<String, Object> params = new HashMap<>();
        params.put("parkId", park.getParkId());
        params.put("campground", campground.getCampgroundName());
        params.put("startDate", arrivalDate);
        params.put("endDate", departureDate);
        params.put("maxOccupancy", maxOccupancy);
        params.put("wheelchairAccessible", wheelchairAccessible);
        params.put("rvLength", rvLength);
        params.put("utilities", utilities);

        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, params);

        while (results.next()) {
            Site site = mapRowToSite(results);
            site.setCost(results.getDouble("Cost"));
            sites.add(site);
        }

        return sites;
    }
}

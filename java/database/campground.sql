select * from campground;
select * from park;
select * from reservation;
select * from site;

select * from reservation join site s on s.site_id = reservation.site_id;

select distinct r.reservation_id, p.park_id, c.campground_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id
         and (('2022-08-08' /*not*/ between from_date and to_date)
            or ('2022-08-18' /*not*/ between from_date and to_date)
            or (
                     (from_date between '2022-08-08' and '2022-08-18')
                     and (to_date between '2022-08-08' and '2022-08-18')
                 ))
    --                                         -- and that start and end date of a current reservation
    --                                         -- are not before and after your :arrivalDate and :departureDate
         join park p on c.park_id = p.park_id -- 643 - 601 = 42(dates) with nulls [without] vs 630 - 605 = 25(dates) with nulls [with the and statement]
where
    r.reservation_id is null
        and
        c.campground_id = 7
  and
        c.park_id = 3;

select s.site_id, p.park_id, c.campground_id, site_number from reservation
    left join site s on reservation.site_id = s.site_id
    left join campground c on s.campground_id = c.campground_id
    left join park p on c.park_id = p.park_id
where p.park_id = 2
  and from_date between current_date and (current_date  interval '30 days') and to_date between current_date and (current_date  interval '30 days');


select
                c.name,
                      site.site_number,
               site.max_occupancy,
               site.accessible,
               site.max_rv_length,
               site.utilities ,
             c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
        from site
            join campground c on site.campground_id = c.campground_id
            left join reservation r on site.site_id = r.site_id
            join park p on c.park_id = p.park_id
        where
              from_date is null or to_date is null or
            ('2022-08-08' not between from_date and to_date)
          and ('2022-08-18' not between from_date and to_date)
          and (
                (from_date not between '2022-08-08' and '2022-08-18')
                and (to_date not between '2022-08-08' and '2022-08-18')
            ) LIMIT 5;

select * from campground;
select * from park;
select * from reservation;
select * from site;

select distinct p.park_id, c.campground_id, s.site_id, s.site_number,
                       s.max_occupancy,
                       s.accessible,
                       s.max_rv_length,
                       s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
                       from site s
                    join campground c on s.campground_id = c.campground_id
                    left join reservation r on s.site_id = r.site_id
                    join park p on c.park_id = p.park_id
                where
                      (c.campground_id = 1 and
                          c.park_id = 1)
                          and
                      ((from_date is null or to_date is null)
                          )
                         or

                    (('2022-08-08' not between from_date and to_date)
                    and ('2022-08-18' not between from_date and to_date)
                    and (
                        (from_date not between '2022-08-08' and '2022-08-18')
                            and (to_date not between '2022-08-08' and '2022-08-18')
                        ));


select distinct  r.reservation_id, c.campground_id, s.site_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id
            and (('2022-08-08' not between from_date and to_date)
            and ('2022-08-18' not between from_date and to_date)
            and (
                     (from_date not between '2022-08-08' and '2022-08-18')
                     and (to_date not between '2022-08-08' and '2022-08-18')
                 ))
         join park p on c.park_id = p.park_id
where
        (c.campground_id = 1 and
         c.park_id = 1)
        and
        ((from_date is null or to_date is null)
            );

select distinct p.park_id, c.campground_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id
            and (('2022-08-18' not between from_date and to_date)
            and ('2022-08-08' not between from_date and to_date)
--             and (
--                      (from_date not between '2022-08-08' and '2022-08-18')
--                      and (to_date not between '2022-08-08' and '2022-08-18')
--                  )
                )
         join park p on c.park_id = p.park_id
where
    r.reservation_id is null and
    (c.campground_id = 1 and
     c.park_id = 1);



select distinct r.reservation_id, c.park_id, c.campground_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id
where (r.site_id not in (select r.site_id from reservation r where not (r.to_date <'2022-08-10' or r.from_date > '2022-08-18')) or
       r.reservation_id is null)
  and
        c.campground_id = 1
  and
        c.park_id = 1;

select distinct r.reservation_id, c.park_id, c.campground_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id
    and not ( r.to_date <'2022-08-08' or r.from_date > '2022-08-18')
     --and that start and end date of a current reservation
     --are not before and after your :arrivalDate and :departureDate
where
    r.reservation_id is null
  and
        c.campground_id = 1
  and
        c.park_id = 1;

select distinct r.reservation_id, p.park_id, c.campground_id, s.site_number,
                s.max_occupancy,
                s.accessible,
                s.max_rv_length,
                s.utilities,
                from_date,
                to_date
--                        c.daily_fee * (date_part('day', '2022-08-18') - date_part('day','2022-08-08')) as Cost
from site s
         join campground c on s.campground_id = c.campground_id
         left join reservation r on s.site_id = r.site_id

    --and that start and end date of a current reservation
    --are not before and after your :arrivalDate and :departtureDate
         join park p on c.park_id = p.park_id
where
    r.reservation_id is null
  and
        c.campground_id = 1
  and
        c.park_id = 1
  and (('2022-08-08' /*not*/ between from_date and to_date)
    or ('2022-08-18' /*not*/ between from_date and to_date)
    or (
               (from_date between '2022-08-08' and '2022-08-18')
               and (to_date between '2022-08-08' and '2022-08-18')
           ))

SELECT distinct site.*, reservation.to_date, reservation.from_date from site
                                left join reservation on site.site_id = reservation.site_id
                                left join campground on site.campground_id = campground.campground_id
where campground.campground_id =1
  and campground.park_id =1
  and (
        (from_date is null and to_date is null)
        or (from_date not between '2022-08-08' and '2022-08-18')
        or (to_date not between '2022-08-08' and '2022-08-18')
        or (from_date < '2022-08-18')
        or (to_date > '2022-08-08')
    )
order by site_number;




select distinct * from reservation r
    join site s on s.site_id = r.site_id
    join campground c on c.campground_id = s.campground_id
    join park p on p.park_id = c.park_id
where (c.campground_id = 1 and
       c.park_id = 1) and
      ((from_date is null or to_date is null)
          )
   or

    (('2022-08-08' not between from_date and to_date)
        and ('2022-08-18' not between from_date and to_date)
        and (
             (from_date not between '2022-08-08' and '2022-08-18')
             and (to_date not between '2022-08-08' and '2022-08-18')
         )) ;

select * from site join campground c on site.campground_id = c.campground_id join reservation r on site.site_id = r.site_id ;

select reservation_id, site_id, name, from_date, to_date, create_date from reservation
                where from_date between current_date and (current_date  interval '30 days') and to_date between current_date and (current_date  interval '30 days');

select * from site
left join reservation r on site.site_id = r.site_id
    and (('2022-08-18' not between from_date and to_date)
        and ('2022-08-08' not between from_date and to_date)
        and (
                 (from_date not between '2022-08-08' and '2022-08-18')
                 and (to_date not between '2022-08-08' and '2022-08-18')
             ))



select reservation_id, s.site_id, c.name, from_date, to_date, create_date from reservation
                    join site s on reservation.site_id = s.site_id
                    join campground c on s.campground_id = c.campground_id
                    join park p on c.park_id = p.park_id
                where p.park_id = 1 and from_date between current_date and (current_date  + interval '30 days')
                  and to_date between current_date and (current_date + interval '30 days');
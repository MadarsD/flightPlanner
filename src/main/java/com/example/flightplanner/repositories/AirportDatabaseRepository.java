package com.example.flightplanner.repositories;

import com.example.flightplanner.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {

    @Query("SELECT a FROM Airport a WHERE " +
            " lower(a.airport) LIKE concat('%', lower(:airport), '%') " +
            "or lower(a.city) LIKE concat('%', lower(:city), '%')" +
            "or lower(a.country) LIKE concat('%', lower(:country), '%')")
    List<Airport> searchAirports(@Param("airport") String airport,
                                 @Param("city") String city,
                                 @Param("country") String country);

}


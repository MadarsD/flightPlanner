package com.example.flightplanner.repositories;

import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightDatabaseRepository extends JpaRepository<Flight, Integer> {

    Optional<Flight> findByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

    @Query("SELECT f FROM Flight f WHERE f.from.airport = :from and f.to.airport = :to and f.departureTime >= :departureDateFrom and f.departureTime < :departureDateTo")
    List<Flight> searchFlights(@Param("from") String from,
                               @Param("to") String to,
                               @Param("departureDateFrom") LocalDateTime departureDateFrom,
                               @Param("departureDateTo") LocalDateTime departureDateTo);

}


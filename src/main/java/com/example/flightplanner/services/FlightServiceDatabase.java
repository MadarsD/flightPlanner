package com.example.flightplanner.services;

import com.example.flightplanner.models.Flight;
import com.example.flightplanner.repositories.AirportDatabaseRepository;
import com.example.flightplanner.repositories.FlightDatabaseRepository;
import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.SearchFlightsRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class FlightServiceDatabase extends AbstractFlightService implements FlightService {

    private final FlightDatabaseRepository flightDatabaseRepository;
    private final AirportDatabaseRepository airportDatabaseRepository;

    public FlightServiceDatabase(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    @Transactional
    @Override
    public Flight addFlight(AddFlightRequest flightRequest) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (isNotValidRequest(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(
                findOrCreate(flightRequest.getFrom()),
                findOrCreate(flightRequest.getTo()),
                flightRequest.getCarrier(),
                LocalDateTime.parse(flightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(flightRequest.getArrivalTime(), formatter)
        );

        if (isInvalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Flight> existingFlight = flightDatabaseRepository.findByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime());

        if (existingFlight.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return flightDatabaseRepository.save(flight);


    }

    @Transactional
    @Override
    public void deleteFlight(int id) {
        if (flightDatabaseRepository.findById(id).isPresent()) {
            flightDatabaseRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public Flight fetchFlightById(int id) {
        return flightDatabaseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public List<Flight> searchFlight(SearchFlightsRequest request) {

        if (isNotValidSearchRequest(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime departureDateFrom = request.getDepartureDate().atStartOfDay();
        LocalDateTime departureDateTo = request.getDepartureDate().plusDays(1).atStartOfDay();

        return flightDatabaseRepository.searchFlights(request.getFrom(), request.getTo(), departureDateFrom, departureDateTo);
    }

    @Transactional
    @Override
    public List<Airport> searchAirports(String name) {
        String trimmed = name.trim();
        return airportDatabaseRepository.searchAirports(trimmed, trimmed, trimmed);
    }

    @Transactional
    @Override
    public void clearFlights() {
        flightDatabaseRepository.deleteAll();
    }

    private Airport findOrCreate(Airport airport) {
        Optional<Airport> existingAirport = airportDatabaseRepository.findById(airport.getAirport());
        return existingAirport.orElseGet(() -> airportDatabaseRepository.save(airport));
    }
}

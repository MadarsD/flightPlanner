package com.example.flightplanner.services;

import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.Flight;
import com.example.flightplanner.models.SearchFlightsRequest;
import com.example.flightplanner.repositories.FlightInMemoryRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class FlightServiceInMemory extends AbstractFlightService implements FlightService {

    private FlightInMemoryRepository flightRepository;

    public FlightServiceInMemory(FlightInMemoryRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest flightRequest) {

        if (isNotValidRequest(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = convertAddFlightToFlight(flightRequest);

        if (isInvalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightRepository.addFlight(flight);
    }

    public synchronized void deleteFlight(int id) {
        flightRepository.deleteFlight(id);
    }

    public Flight fetchFlightById(int id) {
        return flightRepository.fetchFlightById(id);
    }

    public synchronized List<Flight> searchFlight(SearchFlightsRequest request) {
        return flightRepository.searchFlight(request);
    }

    public List<Airport> searchAirports(String name) {
        return flightRepository.searchAirports(name);
    }

    public void clearFlights() {
        flightRepository.clearFlights();
    }

}
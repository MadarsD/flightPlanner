package com.example.flightplanner.services;

import com.example.flightplanner.helperClasses.AddFlightRequest;
import com.example.flightplanner.helperClasses.Airport;
import com.example.flightplanner.helperClasses.Flight;
import com.example.flightplanner.helperClasses.SearchFlightsRequest;
import com.example.flightplanner.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest flightRequest) {
        return flightRepository.addFlight(flightRequest);
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
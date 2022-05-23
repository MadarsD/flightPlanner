package com.example.flightplanner.services;

import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.Flight;
import com.example.flightplanner.models.SearchFlightsRequest;

import java.util.List;


public interface FlightService {

    Flight addFlight(AddFlightRequest flightRequest);

    void deleteFlight(int id);

    Flight fetchFlightById(int id);

    List<Flight> searchFlight(SearchFlightsRequest request);

    List<Airport> searchAirports(String name);

    void clearFlights();
}

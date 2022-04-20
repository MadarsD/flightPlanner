package com.example.flightplanner.Services;

import com.example.flightplanner.HelperClasses.AddFlightRequest;
import com.example.flightplanner.HelperClasses.Airport;
import com.example.flightplanner.HelperClasses.Flight;
import com.example.flightplanner.HelperClasses.SearchFlightsRequest;
import com.example.flightplanner.Repositories.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FlightService {

    private int flightId = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest flightRequest) {

        if (isNotValidRequest(flightRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Flight flight = new Flight(
                flightId,
                flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                LocalDateTime.parse(flightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(flightRequest.getArrivalTime(), formatter)
        );


        if (isInvalidDates(flight)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (flightAlreadyExists(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            flightRepository.flights.add(flight);
            flightId++;
        }

        return flight;
    }

    public synchronized void deleteFlight(int id) {
        flightRepository.flights.removeIf(flight -> flight.getId() == id);
    }

    public Flight fetchFlightById(int id) {
        return flightRepository.flights.stream().filter(flight -> flight.getId() == id).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public synchronized List<Flight> searchFlight(SearchFlightsRequest request) {

        if (isNotValidSearchRequest(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightRepository.flights.stream()
                .filter(flight -> flight.getFrom().getAirport().equals(request.getFrom())
                        && flight.getTo().getAirport().equals(request.getTo())
                        && flight.getDepartureTime().toLocalDate().equals(request.getDepartureDate()))
                .toList();
    }

    public List<Airport> searchAirports(String name) {

        List<Airport> airports = new ArrayList<>();
        String phrase = name.toUpperCase(Locale.ROOT).trim();
        airports.addAll(flightRepository.flights.stream().map(Flight::getFrom).toList());
        airports.addAll(flightRepository.flights.stream().map(Flight::getTo).toList());

        return airports.stream()
                .filter(airport -> airport.getAirport().toUpperCase(Locale.ROOT).contains(phrase)
                        || airport.getCity().toUpperCase(Locale.ROOT).contains(phrase)
                        || airport.getCountry().toUpperCase(Locale.ROOT).contains(phrase)).toList();

    }

    public boolean flightAlreadyExists(Flight flight) {
        return flightRepository.flights.contains(flight);
    }

    public boolean isNotValidRequest(AddFlightRequest flightRequest) {
        return flightRequest.getFrom().getCity().toLowerCase(Locale.ROOT).equals(flightRequest.getTo().getCity().toLowerCase(Locale.ROOT));
    }

    public boolean isNotValidSearchRequest(SearchFlightsRequest request) {
        return request.getFrom().toLowerCase(Locale.ROOT).equals(request.getTo().toLowerCase(Locale.ROOT));
    }

    public boolean isInvalidDates(Flight flight) {
        return flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime());
    }

}
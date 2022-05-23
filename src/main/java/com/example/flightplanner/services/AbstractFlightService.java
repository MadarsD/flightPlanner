package com.example.flightplanner.services;

import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Flight;
import com.example.flightplanner.models.SearchFlightsRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

abstract class AbstractFlightService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight convertAddFlightToFlight(AddFlightRequest flightRequest) {
        return new Flight(
                flightRequest.getFrom(),
                flightRequest.getTo(),
                flightRequest.getCarrier(),
                LocalDateTime.parse(flightRequest.getDepartureTime(), formatter),
                LocalDateTime.parse(flightRequest.getArrivalTime(), formatter)
        );
    }

    protected boolean isNotValidRequest(AddFlightRequest flightRequest) {
        return flightRequest.getFrom().getAirport().toLowerCase(Locale.ROOT).trim().equals(flightRequest.getTo().getAirport().toLowerCase(Locale.ROOT).trim());
    }


    protected boolean isNotValidSearchRequest(SearchFlightsRequest request) {
        return request.getFrom().toLowerCase(Locale.ROOT).equals(request.getTo().toLowerCase(Locale.ROOT));
    }

    protected boolean isInvalidDates(Flight flight) {
        return flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime());
    }
}

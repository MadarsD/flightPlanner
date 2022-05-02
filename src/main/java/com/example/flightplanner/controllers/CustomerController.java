package com.example.flightplanner.controllers;

import com.example.flightplanner.helperClasses.Airport;
import com.example.flightplanner.helperClasses.Flight;
import com.example.flightplanner.helperClasses.PageResult;
import com.example.flightplanner.helperClasses.SearchFlightsRequest;
import com.example.flightplanner.services.FlightService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    FlightService flightService;

    public CustomerController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public List<Airport> searchAirports(@PathParam("search") String search) {
        return flightService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightsRequest request) {
        List<Flight> flightList = flightService.searchFlight(request);
        return new PageResult<>(0, flightList.size(), flightList);
    }

    @GetMapping(value = "/flights/{id}")
    public Flight fetchFlightById(@PathVariable("id") int id) {
        return flightService.fetchFlightById(id);
    }

}
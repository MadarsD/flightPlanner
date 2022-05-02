package com.example.flightplanner.controllers;

import com.example.flightplanner.helperClasses.AddFlightRequest;
import com.example.flightplanner.helperClasses.Flight;
import com.example.flightplanner.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    FlightService flightService;

    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest flightRequest) {
        return flightService.addFlight(flightRequest);
    }

    @GetMapping(value = "/flights/{id}")
    public Flight fetchFlightById(@PathVariable("id") int id) {
        return flightService.fetchFlightById(id);
    }

    @DeleteMapping(value = "/flights/{id}")
    public void deleteFlight(@PathVariable("id") int id) {
        flightService.deleteFlight(id);
    }
}
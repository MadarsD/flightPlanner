package com.example.flightplanner.controllers;

import com.example.flightplanner.services.FlightService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    private FlightService flightService;

    public TestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping("/clear")
    public void clearFlights(){
      flightService.clearFlights();
    }

}
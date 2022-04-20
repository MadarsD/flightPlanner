package com.example.flightplanner.Controllers;

import com.example.flightplanner.Repositories.FlightRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    FlightRepository flightRepository;

    public TestController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @RequestMapping("/clear")
    public void clearFlights(){
        if(!flightRepository.flights.isEmpty()){
            flightRepository.flights.clear();
        }
    }

}
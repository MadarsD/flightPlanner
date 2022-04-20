package com.example.flightplanner.Repositories;

import com.example.flightplanner.HelperClasses.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

   public List<Flight> flights = new ArrayList<>();

}
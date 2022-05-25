package com.example.flightplanner;

import com.example.flightplanner.controllers.AdminController;
import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightPlannerApplicationTests {

    Airport from = new Airport("Latvia", "Riga", "RIX");
    Airport to = new Airport("Estonia", "Tallin", "TLL");
    AddFlightRequest testRequest = new AddFlightRequest(from, to, "Ryanair", "2022-01-01 20:00", "2022-01-01 22:00");

    @Autowired
    AdminController adminController;

    @Test
    void addFlight() {
        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallin", "TLL");
        AddFlightRequest testRequest = new AddFlightRequest(from, to, "Ryanair", "2022-01-01 20:00", "2022-01-01 22:00");
        Flight flight = adminController.addFlight(testRequest);
        Assertions.assertNotNull(flight.getId());
    }

}

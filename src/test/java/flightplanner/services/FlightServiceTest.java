package flightplanner.services;

import com.example.flightplanner.models.AddFlightRequest;
import com.example.flightplanner.models.Airport;
import com.example.flightplanner.models.Flight;
import com.example.flightplanner.services.FlightServiceInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    FlightServiceInMemory flightService;

    @Test
    public void testAddFlight() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Airport from = new Airport("Latvia", "Riga", "RIX");
        Airport to = new Airport("Estonia", "Tallin", "TLL");

        AddFlightRequest testRequest = new AddFlightRequest(from, to, "Ryanair", "2022-01-01 20:00", "2022-01-01 22:00");

        Mockito.doAnswer(invocation -> {
            AddFlightRequest request = invocation.getArgument(0);
            Assertions.assertEquals(testRequest, request);
            return new Flight(1, from, to, "Ryanair", LocalDateTime.parse(request.getDepartureTime(), formatter), LocalDateTime.parse(request.getArrivalTime(), formatter));
        }).when(flightService).addFlight(testRequest);

        Flight firstFlight = flightService.addFlight(testRequest);

        Mockito.verify(flightService).addFlight(testRequest);
    }
}

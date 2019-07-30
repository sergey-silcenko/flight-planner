package io.codelex.flightplanner.testing;

import io.codelex.flightplanner.flights.FlightsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {
    private FlightsRepository flightsRepository;

    public TestingController(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightsRepository.clearFlights();
    }
}

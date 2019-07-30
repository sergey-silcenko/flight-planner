package io.codelex.flightplanner.authorizationcontroller;

import io.codelex.flightplanner.config.PageResult;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.SearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/api")
public class AuthorizationAdminController {
    private FlightsRepository flightsRepository;

    public AuthorizationAdminController(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @PostMapping("/flights/search")
    public PageResult<FlightDTO> searchFlights(@RequestBody SearchRequest searchRequest) {
        List<FlightDTO> flight = new ArrayList<>();
        if (flightsRepository.emptyValueSearch(searchRequest)) {
            flight = flightsRepository.searchFlights(searchRequest);
        }
        return new PageResult<>(0, flight.size(), flight);
    }


    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightDTO> fetchFlights(@PathVariable("id") Long id) {
        FlightDTO flight = flightsRepository.findById(id);

        if (flight != null) {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/airports")
    public List<Airport> findAirport(@RequestParam("search") String search) {
        return flightsRepository.searchAirports(search);
    }


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    void handleIllegalArgument() {
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    void handleIllegalState() {
    }
}

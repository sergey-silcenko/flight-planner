package io.codelex.flightplanner.admin;

import io.codelex.flightplanner.dto.AddFlightRequestDTO;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.flights.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {
    private FlightsRepository flightsRepository;

    @Autowired
    public AdminController(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity fetchFlights() {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/flights")
    public ResponseEntity<FlightDTO> addFlights(@RequestBody AddFlightRequestDTO requestDTO) {
        FlightDTO flightDTO = flightsRepository.add(requestDTO);
        return new ResponseEntity<>(flightDTO, CREATED);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlights(@PathVariable("id") Long id) {
        flightsRepository.removeById(id);
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

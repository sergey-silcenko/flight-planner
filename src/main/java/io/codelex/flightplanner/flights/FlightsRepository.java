package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.dto.AddFlightRequestDTO;
import io.codelex.flightplanner.dto.FlightDTO;
import io.codelex.flightplanner.model.Airport;
import io.codelex.flightplanner.model.SearchRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightsRepository {

    private Long id = 0L;
    private List<FlightDTO> flightDTOS = new ArrayList<>();

    public synchronized FlightDTO add(AddFlightRequestDTO request) {
        synchronized (flightDTOS) {
            for (FlightDTO flightDTO : flightDTOS) {

                if (flightDTO.getFrom().equals(request.getFrom()) &&
                        flightDTO.getTo().equals(request.getTo()) &&
                        flightDTO.getDepartureTime().equals(request.getDepartureTime()) &&
                        flightDTO.getArrivalTime().equals(request.getArrivalTime()) &&
                        flightDTO.getCarrier().equals(request.getCarrier())) {
                    throw new IllegalStateException();
                }
            }
            //Input name Not Null
            if (request.getFrom() == null
                    || request.getTo() == null
                    || request.getCarrier() == null
                    || request.getArrivalTime() == null
                    || request.getDepartureTime() == null
                    || request.getFrom().getCountry() == null
                    || request.getFrom().getCity() == null
                    || request.getFrom().getAirport() == null
                    || request.getTo().getCountry() == null
                    || request.getTo().getCity() == null
                    || request.getTo().getAirport() == null) {
                throw new IllegalArgumentException();
            }

            //Input name Not Empty
            if (request.getFrom().getCountry().length() == 0
                    || request.getFrom().getCity().length() == 0
                    || request.getFrom().getAirport().length() == 0) {
                throw new IllegalArgumentException();
            }
            if (request.getTo().getCountry().length() == 0
                    || request.getTo().getCity().length() == 0
                    || request.getTo().getAirport().length() == 0
                    || request.getCarrier().length() == 0) {
                throw new IllegalArgumentException();
            }

            //Logic time
            if (request.getDepartureTime().isAfter(request.getArrivalTime())
                    || request.getDepartureTime().equals(request.getArrivalTime())) {
                throw new IllegalArgumentException();
            }

            //Logic to the same destination
            if (request.getFrom().equals(request.getTo())
                    || request.getFrom().getAirport().equals(request.getTo().getAirport())
                    || request.getFrom().getCity().equals(request.getTo().getCity())
                    || request.getFrom().getCountry().equals(request.getTo().getCountry())
                    || request.getFrom().getAirport().toLowerCase().equals(request.getTo().getAirport())
                    || request.getFrom().getCity().toLowerCase().equals(request.getTo().getCity())
                    || request.getFrom().getCountry().toLowerCase().equals(request.getTo().getCountry())) {
                throw new IllegalArgumentException();
            }

            id++;
            FlightDTO flightDTO = new FlightDTO();
            flightDTO.setId(id);
            flightDTO.setFrom(request.getFrom());
            flightDTO.setTo(request.getTo());
            flightDTO.setArrivalTime(request.getArrivalTime());
            flightDTO.setDepartureTime(request.getDepartureTime());
            flightDTO.setCarrier(request.getCarrier());
            flightDTOS.add(flightDTO);
            return flightDTO;
        }
    }

    public List<FlightDTO> searchFlights(SearchRequest searchRequest) {
        return new ArrayList<>();
    }

    public List<Airport> searchAirports(String search) {
        List<Airport> airports = new ArrayList<>();
        for (FlightDTO flightDTO : flightDTOS) {
            Airport from = flightDTO.getFrom();
            if (from.getCity().toLowerCase().startsWith(search.trim().toLowerCase())
                    || from.getCountry().toLowerCase().startsWith(search.toLowerCase().trim())
                    || from.getAirport().toLowerCase().startsWith(search.toLowerCase().trim())) {
                airports.add(from);
            }
        }

        return airports;
    }

    public boolean emptyValueSearch(SearchRequest request) {
        if (request.getTo() == null
                || request.getFrom() == null
                || request.getDepartureDate() == null
        ) {
            throw new IllegalArgumentException();
        }
        if (request.getFrom().toLowerCase().trim().equals(request.getTo().toLowerCase().trim())) {
            throw new IllegalArgumentException();
        }
        return true;
    }


    public FlightDTO findById(Long id) {

        for (FlightDTO flightDTO : flightDTOS) {
            if (flightDTO.getId().equals(id)) {
                return flightDTO;
            }
        }
        return null;
    }

    public synchronized void removeById(Long id) {
        FlightDTO flightForDeletion = null;

        for (FlightDTO flightDTO : flightDTOS) {
            if (flightDTO.getId().equals(id)) {
                flightForDeletion = flightDTO;
            }
        }

        if (flightForDeletion != null) {
            flightDTOS.remove(flightForDeletion);
        }
    }

    public void clearFlights() {
        flightDTOS.clear();
    }
}

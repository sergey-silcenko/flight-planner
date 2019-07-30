package io.codelex.flightplanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Airport {

    private String country;
    private String city;
    private String airport;

    public Airport(@JsonProperty("country") String country,
                   @JsonProperty("city") String city,
                   @JsonProperty("airport") String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return airport.equals(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airport);
    }
}

package com.finalproject.finalproject.repositories;

import com.finalproject.finalproject.entity.Flights;
import com.finalproject.finalproject.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flights, Long> {
    ArrayList<Flights> getFlightsByFlightStatus(FlightStatus flightStatus);
}

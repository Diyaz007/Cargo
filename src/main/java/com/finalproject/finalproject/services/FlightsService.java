package com.finalproject.finalproject.services;

import com.finalproject.finalproject.Exceptions.NotFreeEmployer;
import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.FlightsResponse;
import com.finalproject.finalproject.entity.Employer;
import com.finalproject.finalproject.entity.Flights;
import com.finalproject.finalproject.enums.TripStatus;
import com.finalproject.finalproject.enums.WorkStatus;
import com.finalproject.finalproject.enums.FlightStatus;
import com.finalproject.finalproject.repositories.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FlightsService {
    @Autowired
    private FlightsRepository flightsRepository;
    @Autowired
    private EmployerService employerService;

    public Flights saveFlight(FlightsResponse flight) {
        HashMap<String,Integer> errors = new HashMap<>();
        try {
        Optional<Employer> employer = employerService.findEmployerById(flight.getEmployerId());
        if(employer.get().getWorkStatus() != WorkStatus.WAITING_FLIGHT){
            throw new NotFreeEmployer("Employer is not waiting flight");
        }
        Flights newFlight = new Flights();
        newFlight.setEmployerId(employer.get());
        newFlight.setMaxWeight(flight.getMaxWeight());
        newFlight.setFreeWeight(0);
        newFlight.setStartTrip(new Date());
        newFlight.setEndTrip(null);
        newFlight.setFlightStatus(FlightStatus.NEW);
        newFlight.setTripStatus(TripStatus.WAIT);
        Employer employer1 = employer.get();
        employer1.setWorkStatus(WorkStatus.WORKING);
        employer1.setTripStatus(TripStatus.WAIT);
        employerService.updateEmployer(employer1);
        return flightsRepository.save(newFlight);
        } catch (NotFreeEmployer e){
            errors.put("Employer is busy",400);
            throw new SignUpException("Employer is busy",errors);
        }
        catch (Exception e) {
            errors.put("Employer not found",404);
            throw new SignUpException("Employer not found",errors);
        }
    }
    public Flights getFlightById(Long id) {
        Flights flights = flightsRepository.getReferenceById(id);
        if(flights == null){
            HashMap<String,Integer> errors = new HashMap<>();
            errors.put("flight not found",404);
            throw new SignUpException("flight not found",errors);
        }
        return flights;
    }
    public Flights updateFlights(Flights flights) {
        try {
            return flightsRepository.save(flights);
        }catch (Exception e){
            HashMap<String,Integer> errors = new HashMap<>();
            errors.put("Flight not found",404);
            throw new SignUpException("Update Flight Error",errors);
        }
    }
}

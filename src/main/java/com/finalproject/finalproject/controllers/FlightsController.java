package com.finalproject.finalproject.controllers;

import com.finalproject.finalproject.Exceptions.SignUpException;
import com.finalproject.finalproject.dto.FlightsByFlightStatusRequest;
import com.finalproject.finalproject.dto.FlightsResponse;
import com.finalproject.finalproject.dto.UpdateTripStatus;
import com.finalproject.finalproject.entity.*;
import com.finalproject.finalproject.enums.FlightStatus;
import com.finalproject.finalproject.enums.Roles;
import com.finalproject.finalproject.enums.WorkStatus;
import com.finalproject.finalproject.services.EmployerService;
import com.finalproject.finalproject.services.FlightsService;
import com.finalproject.finalproject.services.HistoryService;
import com.finalproject.finalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/flights")
@RestController
public class FlightsController {
    @Autowired
    private FlightsService flightsService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private HistoryService historyService;

    @PostMapping("/create")
    public ResponseEntity<Object> addFlight(@RequestBody FlightsResponse flight) {
        try {
            Map<String,Integer> errors = new HashMap<>();
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users == null) {
                errors.put("not found",404);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.ADMIN) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            Flights flights = flightsService.saveFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body(flights);
        }catch (SignUpException e){
            HashMap<String,Integer> errors = (HashMap<String, Integer>) e.getErrors();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }
    @PutMapping("/trip-status-update")
    public ResponseEntity<Object> updateTripStatus(@RequestBody UpdateTripStatus updateTripStatus) {
        Map<String,Integer> errors = new HashMap<>();
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.EMPLOYER && users.getRole() != Roles.ADMIN) {
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
            }
            Flights flights = flightsService.getFlightById(updateTripStatus.getFlightsID());
            flights.setTripStatus(updateTripStatus.getTripStatus());
            switch (updateTripStatus.getTripStatus()){
                case WAIT -> flights.setFlightStatus(FlightStatus.NEW);
                case IN_PROGRESS -> flights.setFlightStatus(FlightStatus.IN_PROGRESS);
                case FINISHED ->{
                    flights.setFlightStatus(FlightStatus.FINISHED);
                    flights.setEndTrip(new Date());
                    History history = new History();
                    history.setFlights(flights);
                    history.setFinishedDate(new Date());
                    historyService.save(history);
                    Employer employer = employerService.findEmployerById(Long.valueOf(flights.getEmployerId().getId())).get();
                    employer.setWorkStatus(WorkStatus.WAITING_FLIGHT);
                }
                case CANCEL -> flights.setFlightStatus(FlightStatus.CANCELLED);
            }

            Employer employer = flights.getEmployerId();
            employer.setTripStatus(updateTripStatus.getTripStatus());

            employerService.updateEmployer(employer);
            flightsService.updateFlights(flights);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SignUpException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
        }
    }
    @GetMapping("/allHistory")
    public ResponseEntity<List<History>> getAllHistory() {
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.ADMIN) {
                Map<String,Integer> errors = new HashMap<>();
                errors.put("access denied",403);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<History> histories = historyService.findAll();
            return ResponseEntity.ok(histories);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Flights>> allFlights() {
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<Flights> flights = flightsService.getAllFlights();
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/allByStatus")
    public ResponseEntity<List<Flights>> allFlightsByStatus(@RequestBody FlightsByFlightStatusRequest request) {
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Users users = usersService.findByEmail(authentication.getName());
            if(users.getRole() != Roles.MANAGER && users.getRole() != Roles.ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<Flights> flights = flightsService.getFlightsByFlightStatus(request.getFlightStatus());
            return ResponseEntity.ok(flights);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.finalproject.finalproject.dto;

import com.finalproject.finalproject.enums.FlightStatus;

public class FlightsByFlightStatusRequest {
    private FlightStatus flightStatus;

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }
}

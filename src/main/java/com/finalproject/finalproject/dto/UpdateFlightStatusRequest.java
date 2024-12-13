package com.finalproject.finalproject.dto;

import com.finalproject.finalproject.enums.FlightStatus;

public class UpdateFlightStatusRequest {
    private int flightId;
    private FlightStatus status;

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}

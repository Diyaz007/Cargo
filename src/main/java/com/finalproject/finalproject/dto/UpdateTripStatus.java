package com.finalproject.finalproject.dto;

import com.finalproject.finalproject.enums.TripStatus;

public class UpdateTripStatus {
    private Long flightsID;
    private TripStatus tripStatus;

    public UpdateTripStatus(Long flightsID, TripStatus tripStatus) {
        this.flightsID = flightsID;
        this.tripStatus = tripStatus;
    }

    public long getFlightsID() {
        return flightsID;
    }

    public void setFlightsID(Long flightsID) {
        this.flightsID = flightsID;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}

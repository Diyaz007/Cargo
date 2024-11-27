package com.finalproject.finalproject.dto;

import com.finalproject.finalproject.enums.TripStatus;

public class UpdateTripStatus {
    private int userId;
    private TripStatus tripStatus;

    public UpdateTripStatus(int userId, TripStatus tripStatus) {
        this.userId = userId;
        this.tripStatus = tripStatus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}

package com.finalproject.finalproject.entity;

import com.finalproject.finalproject.enums.TripStatus;
import com.finalproject.finalproject.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "flights")
@Getter
@Setter
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_id_seq")
    @SequenceGenerator(name = "flight_id_seq", sequenceName = "flight_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true, nullable = false)
    private Employer employerId;

    @Column(name = "MAX_WEIGHT")
    private Integer maxWeight;

    @Column(name = "FREE_WEIGHT")
    private Integer freeWeight;

    @Column(name = "TRIP_STATUS")
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    @Column(name = "FLIGHT_STATUS")
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    @Column(name = "START_TRIP")
    private Date startTrip;

    @Column(name = "END_TRIP")
    private Date endTrip;




}

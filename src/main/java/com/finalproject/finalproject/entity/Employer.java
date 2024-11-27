package com.finalproject.finalproject.entity;

import com.finalproject.finalproject.enums.TripStatus;
import com.finalproject.finalproject.enums.UserStatus;
import com.finalproject.finalproject.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table(name = "employer")
@Entity
@Getter
@Setter
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employer_id_seq")
    @SequenceGenerator(name = "employer_id_seq", sequenceName = "employer_id_seq", allocationSize = 1)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true, nullable = false)
    private Users userId;

    @Column(name = "TRIP_STATUS")
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    @Column(name = "WORK_STATUS")
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;





}

package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long userId;
    @Column(name="easy_card_number")
    private String easyCardNumber;
    @Column(name="bikeuid")
    private String bikeUID;
    @Column(name="rent_station")
    private String rentStation;
    @Column(name="rent_stationuid")
    private String rentStationUid;
    @Column(name="rent_time")
    private LocalDateTime rentTime;
    @Column(name="return_station")
    private String returnStation;
    @Column(name="return_stationuid")
    private String returnStationUid;
    @Column(name="return_time")
    private LocalDateTime returnTime;
    @Column(name="amount")
    private double amount;
}


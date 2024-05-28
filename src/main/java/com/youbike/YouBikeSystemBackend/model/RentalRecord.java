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

    private Long userId;
    private String easyCardNumber;
    private String bikeUID;
    private String rentStation;
    private LocalDateTime rentTime;
    private String returnStation;
    private LocalDateTime returnTime;
    private double amount;
}


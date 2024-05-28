package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String easyCardId;
    private Long userId;
    private Long bikeId;
    private LocalDateTime transactionTime;
    private double amount;
}


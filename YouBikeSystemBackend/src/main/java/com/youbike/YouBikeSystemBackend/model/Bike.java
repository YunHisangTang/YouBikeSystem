package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bikeUID;
    private String authorityID;
    private String type;  // "Normal" 或 "Electric"
    private String status;  // "非營運中", "營運中", "租用中", "遺失"
    private String currentLocation;  // 站點+車柱
    private String maintenanceInfo;
}

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

    @Column(name = "bikeuid")
    private String bikeUID;
    @Column(name = "authorityid")
    private String authorityID;
    @Column(name = "type")
    private String type;  // "Normal" 或 "Electric"
    @Column(name = "status")
    private String status;  // "非營運中"(Out of Service), "營運中"(Operational), "租用中"(Rented), "遺失"(Lost)
    @Column(name = "maintenance_info")
    private String maintenanceInfo;
}

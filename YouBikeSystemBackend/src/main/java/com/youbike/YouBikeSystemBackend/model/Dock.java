package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stationuid")
    private String stationUID;
    @Column(name = "dockuid")
    private String dockUID;
    @Column(name = "dockid")
    private String dockID;
    @Column(name = "bikeuid")
    private String bikeUID;
    @Column(name = "status")
    private String status; // "非營運中"(Out of Service), "營運中"(Operational)
    @Column(name = "maintenance_info")
    private String maintenanceInfo;
}


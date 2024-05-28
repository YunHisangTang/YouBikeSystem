package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportId;
    private String bikeUID;
    private String dockUID;
    private String reportDetail;
    private String reportStatus;
    private String reportTime;
}


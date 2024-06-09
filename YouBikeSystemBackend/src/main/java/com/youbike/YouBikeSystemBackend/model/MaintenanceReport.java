package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "assets_uid")
    private String assetsUID;

    @Column(name = "report_detail")
    private String reportDetail;

    @Column(name = "report_status")
    private String reportStatus;

    @Column(name = "report_time")
    private LocalDateTime reportTime;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "solve_time")
    private LocalDateTime solveTime;

    @Column(name = "maintainer_id")
    private Long maintainerId;
}

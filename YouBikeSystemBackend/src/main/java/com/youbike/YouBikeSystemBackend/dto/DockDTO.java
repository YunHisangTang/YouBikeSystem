package com.youbike.YouBikeSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DockDTO {
    private Long id;
    private String stationUID;
    private String dockUID;
    private String dockID;
    private String bikeUID;
    private String status;
    private String maintenanceInfo;
}

package com.youbike.YouBikeSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDTO {
    private Long id;
    private String stationUID;
    private String stationID;
    private String authorityID;
    private String nameZhTw;
    private String nameEn;
    private double positionLon;
    private double positionLat;
    private String addressZhTw;
    private String addressEn;
    private int bikesCapacity;
    private List<DockDTO> docks;
    private int availableSpaces; // 空位數量
    private int availableBikes; // 營運中自行車數量
    private List<String> bikeTypes; // 營運中的車種
}

package com.youbike.YouBikeSystemBackend.dto;

import lombok.Data;

@Data
public class MoveSpecificBikeRequest {
    private String bikeUID;
    private String destinationStationUID;

}

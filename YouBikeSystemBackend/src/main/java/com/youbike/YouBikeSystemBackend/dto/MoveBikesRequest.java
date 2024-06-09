package com.youbike.YouBikeSystemBackend.dto;

import lombok.Data;

@Data
public class MoveBikesRequest {
    private String sourceStationUID;
    private String destinationStationUID;
    private int numberOfBikes;

}

package com.youbike.YouBikeSystemBackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentBikeDTO {
    private String bikeUID;
    private String stationUID;
    private LocalDateTime startTime;
    private String userPhoneNumber;
}

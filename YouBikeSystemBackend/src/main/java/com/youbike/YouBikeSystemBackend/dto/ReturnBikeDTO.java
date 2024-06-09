package com.youbike.YouBikeSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBikeDTO {
    private String stationUID;
    private LocalDateTime endTime;
    private String userPhoneNumber;
}

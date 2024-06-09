package com.youbike.YouBikeSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecordDTO {
    private Long id;
    private Long userId;
    private String easyCardNumber;
    private String bikeUID;
    private String rentStation;
    private String rentStationUid;
    private LocalDateTime rentTime;
    private String returnStation;
    private String returnStationUid;
    private LocalDateTime returnTime;
    private double amount;
}

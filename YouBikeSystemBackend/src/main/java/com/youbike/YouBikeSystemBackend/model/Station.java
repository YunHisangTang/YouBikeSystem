package com.youbike.YouBikeSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}

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

    @Column(name = "stationuid")
    private String stationUID;

    @Column(name = "stationid")
    private String stationID;

    @Column(name = "authorityid")
    private String authorityID;

    @Column(name = "name_zh_tw")
    private String nameZhTw;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "position_lon")
    private Double positionLon;

    @Column(name = "position_lat")
    private Double positionLat;

    @Column(name = "address_zh_tw")
    private String addressZhTw;

    @Column(name = "address_en")
    private String addressEn;

    @Column(name = "bikes_capacity")
    private Integer bikesCapacity;
}

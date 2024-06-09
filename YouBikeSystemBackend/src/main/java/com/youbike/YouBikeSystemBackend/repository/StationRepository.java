package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Query(value = "SELECT * FROM Station WHERE name_zh_tw LIKE CONCAT('%', :name, '%') OR name_en LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Station> findByNameZhTwContainingOrNameEnContaining(@Param("name") String name);

    @Query(value = "SELECT * FROM Station s WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(s.position_lat)) * cos(radians(s.position_lon) - radians(:lon)) + sin(radians(:lat)) * sin(radians(s.position_lat)))) < :range", nativeQuery = true)
    List<Station> findByLocationWithinDistance(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("range") double range);

    @Query(value = "SELECT DISTINCT s.* FROM Station s WHERE s.stationuid IN (SELECT d.stationuid FROM Dock d WHERE d.bikeuid IS NOT NULL AND d.bikeuid != '' AND d.status = 'Operational' AND d.bikeuid IN (SELECT b.bikeuid FROM Bike b WHERE b.status = 'Operational'))", nativeQuery = true)
    List<Station> findByHasBikes();

    @Query(value = "SELECT DISTINCT s.* FROM Station s WHERE s.stationuid IN (SELECT d.stationuid FROM Dock d WHERE (d.bikeuid IS NULL OR d.bikeuid = '') AND d.status = 'Operational')", nativeQuery = true)
    List<Station> findByHasSlots();

    Optional<Station> findByStationUID(String stationUID);
}

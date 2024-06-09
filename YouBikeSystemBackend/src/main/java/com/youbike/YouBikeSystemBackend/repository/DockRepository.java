package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.Dock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DockRepository extends JpaRepository<Dock, Long> {

    @Query("SELECT d FROM Dock d WHERE d.stationUID = :stationUID AND d.status = 'Operational'")
    List<Dock> findOperationalDocksByStationUID(String stationUID);

    Optional<Dock> findByBikeUID(String bikeUID);
    List<Dock> findByStationUID(String stationUID);

    @Query(value = "SELECT * FROM dock WHERE stationuid = :stationUID AND (bikeuid IS NULL OR bikeuid = '') AND status = 'Operational' LIMIT 1", nativeQuery = true)
    Optional<Dock> findFirstAvailableDock(@Param("stationUID") String stationUID);


    Optional<Dock> findByDockUID(String dockUID);
}

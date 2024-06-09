package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.model.Dock;
import com.youbike.YouBikeSystemBackend.repository.BikeRepository;
import com.youbike.YouBikeSystemBackend.repository.DockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DockService {
    @Autowired
    private DockRepository dockRepository;

    @Autowired
    private BikeRepository bikeRepository;

    public Optional<Bike> findAvailableBike(String stationUID, String bikeType) {
        return dockRepository.findOperationalDocksByStationUID(stationUID).stream()
                .map(dock -> bikeRepository.findByBikeUID(dock.getBikeUID()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(bike -> bike.getStatus().equals("Operational") && bike.getType().equalsIgnoreCase(bikeType))
                .findAny();
    }

    public List<Dock> getAllDocks() {
        return dockRepository.findAll();
    }

    public Optional<Dock> findByDockUID(String dockUID) {
        return dockRepository.findByDockUID(dockUID);
    }

    public Dock saveDock(Dock dock) {
        return dockRepository.save(dock);
    }
}

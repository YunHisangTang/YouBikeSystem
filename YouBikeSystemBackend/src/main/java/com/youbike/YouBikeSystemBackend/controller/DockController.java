package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.model.Dock;
import com.youbike.YouBikeSystemBackend.service.DockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docks")
@CrossOrigin("*")
public class DockController {
    @Autowired
    private DockService dockService;

    @GetMapping("/findAvailableBike")
    public ResponseEntity<Bike> findAvailableBike(@RequestParam String stationUID, @RequestParam String bikeType) {
        return dockService.findAvailableBike(stationUID, bikeType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dock>> getAllDocks() {
        List<Dock> docks = dockService.getAllDocks();
        return ResponseEntity.ok(docks);
    }

    @GetMapping("/{dockUID}")
    public ResponseEntity<Dock> getDockStatus(@PathVariable String dockUID) {
        Optional<Dock> dock = dockService.findByDockUID(dockUID);
        return dock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{dockUID}")
    public ResponseEntity<Dock> updateDockStatus(@PathVariable String dockUID, @RequestBody Dock dock) {
        Optional<Dock> existingDock = dockService.findByDockUID(dockUID);
        if (existingDock.isPresent()) {
            Dock updatedDock = existingDock.get();
            updatedDock.setStatus(dock.getStatus());
            dockService.saveDock(updatedDock);
            return ResponseEntity.ok(updatedDock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

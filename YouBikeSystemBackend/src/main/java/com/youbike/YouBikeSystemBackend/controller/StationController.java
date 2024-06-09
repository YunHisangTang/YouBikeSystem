package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.MoveBikesRequest;
import com.youbike.YouBikeSystemBackend.dto.MoveSpecificBikeRequest;
import com.youbike.YouBikeSystemBackend.dto.StationDTO;
import com.youbike.YouBikeSystemBackend.model.Station;
import com.youbike.YouBikeSystemBackend.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin("*")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/search")
    public List<StationDTO> searchStations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) Double range,
            @RequestParam(required = false) Boolean hasBike,
            @RequestParam(required = false) Boolean hasSpace,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<StationDTO> result = stationService.searchStations(name, lat, lon, range, hasBike, hasSpace, page, size);
        return result.isEmpty() ? Collections.emptyList() : result;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Station>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{stationUID}/info")
    public ResponseEntity<Map<String, Integer>> getStationInfo(@PathVariable String stationUID) {
        return ResponseEntity.ok(stationService.getStationInfo(stationUID));
    }

    @GetMapping("/{stationUID}/bikes")
    public ResponseEntity<List<String>> getBikesInStation(@PathVariable String stationUID) {
        return ResponseEntity.ok(stationService.getBikesInStation(stationUID));
    }

    @PostMapping("/moveBikes")
    public ResponseEntity<Map<String, String>> moveBikes(@RequestBody MoveBikesRequest moveBikesRequest) {
        try {
            stationService.moveBikes(moveBikesRequest.getSourceStationUID(), moveBikesRequest.getDestinationStationUID(), moveBikesRequest.getNumberOfBikes());
            Map<String, String> response = Map.of("message", "Bikes moved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = Map.of("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/moveSpecificBike")
    public ResponseEntity<Map<String, String>> moveSpecificBike(@RequestBody Map<String, String> request) {
        String bikeUID = request.get("bikeUID");
        String destinationStationUID = request.get("destinationStationUID");

        return ResponseEntity.ok(stationService.moveSpecificBike(bikeUID, destinationStationUID));
    }
}

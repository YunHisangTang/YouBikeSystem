
package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.RentBikeDTO;
import com.youbike.YouBikeSystemBackend.dto.ReturnBikeDTO;
import com.youbike.YouBikeSystemBackend.dto.StationDTO;
import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bikes")
@CrossOrigin("*")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @PostMapping("/rent")
    public ResponseEntity<Map<String, String>> rentBike(@RequestBody RentBikeDTO rentBikeDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            StationDTO stationInfo = bikeService.rentBike(rentBikeDTO);
            response.put("message", "Bike rented successfully");
            response.put("availableBikes", String.valueOf(stationInfo.getAvailableBikes()));
            response.put("availableSpaces", String.valueOf(stationInfo.getAvailableSpaces()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, String>> returnBike(@RequestBody ReturnBikeDTO returnBikeDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            StationDTO stationInfo = bikeService.returnBike(returnBikeDTO);
            response.put("message", "Bike returned successfully");
            response.put("availableBikes", String.valueOf(stationInfo.getAvailableBikes()));
            response.put("availableSpaces", String.valueOf(stationInfo.getAvailableSpaces()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bike>> getAllBikes() {
        List<Bike> bikes = bikeService.getAllBikes();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/{bikeUID}")
    public ResponseEntity<Bike> getBikeStatus(@PathVariable String bikeUID) {
        Optional<Bike> bike = bikeService.findByBikeUID(bikeUID);
        return bike.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{bikeUID}")
    public ResponseEntity<Bike> updateBikeStatus(@PathVariable String bikeUID, @RequestBody Bike bike) {
        Optional<Bike> existingBike = bikeService.findByBikeUID(bikeUID);
        if (existingBike.isPresent()) {
            Bike updatedBike = existingBike.get();
            updatedBike.setStatus(bike.getStatus());
            bikeService.saveBike(updatedBike);
            return ResponseEntity.ok(updatedBike);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


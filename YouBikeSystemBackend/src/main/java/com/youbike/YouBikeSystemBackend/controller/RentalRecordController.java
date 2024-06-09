package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.RentalRecordDTO;
import com.youbike.YouBikeSystemBackend.model.RentalRecord;
import com.youbike.YouBikeSystemBackend.service.RentalRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentalRecords")
@CrossOrigin("*")
public class RentalRecordController {
    @Autowired
    private RentalRecordService rentalRecordService;

    @GetMapping("/current")
    public ResponseEntity<RentalRecord> getCurrentRentalRecord(@RequestParam String easyCardNumber) {
        Optional<RentalRecord> rentalRecord = rentalRecordService.getCurrentRentalRecord(easyCardNumber);
        if (rentalRecord.isPresent()) {
            return ResponseEntity.ok(rentalRecord.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/query")
    public ResponseEntity<List<RentalRecordDTO>> getRentalRecordsByDateRange(
            @RequestParam String startDate, @RequestParam String endDate) {
        List<RentalRecord> rentalRecords = rentalRecordService.getRentalRecordsByDateRange(startDate, endDate);
        List<RentalRecordDTO> rentalRecordDTOs = rentalRecords.stream()
                .map(record -> {
                    RentalRecordDTO dto = new RentalRecordDTO();
                    BeanUtils.copyProperties(record, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalRecordDTOs);
    }

    @GetMapping("/crossRegionReturns")
    public ResponseEntity<List<RentalRecord>> getCrossRegionReturns() {
        return ResponseEntity.ok(rentalRecordService.getCrossRegionReturns());
    }
}

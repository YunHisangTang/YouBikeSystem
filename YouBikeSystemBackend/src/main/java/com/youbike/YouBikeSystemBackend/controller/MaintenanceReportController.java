package com.youbike.YouBikeSystemBackend.controller;

import com.youbike.YouBikeSystemBackend.dto.MaintenanceReportDTO;
import com.youbike.YouBikeSystemBackend.model.MaintenanceReport;
import com.youbike.YouBikeSystemBackend.service.MaintenanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenanceReports")
@CrossOrigin("*")
public class MaintenanceReportController {
    @Autowired
    private MaintenanceReportService maintenanceReportService;

    @PostMapping("/create")
    public ResponseEntity<MaintenanceReportDTO> createReport(@RequestBody MaintenanceReportDTO reportDTO) {
        MaintenanceReportDTO createdReport = maintenanceReportService.createReport(reportDTO);
        return ResponseEntity.ok(createdReport);
    }

    @GetMapping("/user/{reporterId}")
    public ResponseEntity<List<MaintenanceReport>> getReportsByReporterId(@PathVariable Long reporterId) {
        List<MaintenanceReport> reports = maintenanceReportService.getReportsByReporterId(reporterId);
        return ResponseEntity.ok(reports);
    }
}

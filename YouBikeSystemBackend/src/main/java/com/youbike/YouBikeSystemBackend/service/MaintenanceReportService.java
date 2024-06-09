package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.dto.MaintenanceReportDTO;
import com.youbike.YouBikeSystemBackend.model.MaintenanceReport;
import com.youbike.YouBikeSystemBackend.repository.MaintenanceReportRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceReportService {
    @Autowired
    private MaintenanceReportRepository maintenanceReportRepository;

    public MaintenanceReportDTO createReport(MaintenanceReportDTO reportDTO) {
        MaintenanceReport report = new MaintenanceReport();
        BeanUtils.copyProperties(reportDTO, report);
        report.setReportTime(LocalDateTime.now());
        report.setReportStatus("Pending");
        maintenanceReportRepository.save(report);
        return reportDTO;
    }

    public List<MaintenanceReport> getReportsByReporterId(Long reporterId) {
        return maintenanceReportRepository.findByReporterId(reporterId);
    }
}

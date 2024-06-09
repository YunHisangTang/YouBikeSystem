package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.MaintenanceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceReportRepository extends JpaRepository<MaintenanceReport, Long> {
    List<MaintenanceReport> findByReporterId(Long reporterId);
}

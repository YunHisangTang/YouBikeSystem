package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.RentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long> {
    Optional<RentalRecord> findFirstByEasyCardNumberAndReturnTimeIsNullOrderByRentTimeDesc(String easyCardNumber);
    List<RentalRecord> findByRentTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM RentalRecord r WHERE SUBSTRING(r.rentStationUid, 1, 3) <> SUBSTRING(r.returnStationUid, 1, 3)")
    List<RentalRecord> findCrossRegionReturns();
}

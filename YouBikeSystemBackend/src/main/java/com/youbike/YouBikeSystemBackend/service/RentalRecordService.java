package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.model.RentalRecord;
import com.youbike.YouBikeSystemBackend.repository.RentalRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalRecordService {
    @Autowired
    private RentalRecordRepository rentalRecordRepository;

    public Optional<RentalRecord> getCurrentRentalRecord(String easyCardNumber) {
        return rentalRecordRepository.findFirstByEasyCardNumberAndReturnTimeIsNullOrderByRentTimeDesc(easyCardNumber);
    }

    public List<RentalRecord> getRentalRecordsByDateRange(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        return rentalRecordRepository.findByRentTimeBetween(startDateTime, endDateTime);
    }

    public List<RentalRecord> getCrossRegionReturns() {
        return rentalRecordRepository.findCrossRegionReturns();
    }
}

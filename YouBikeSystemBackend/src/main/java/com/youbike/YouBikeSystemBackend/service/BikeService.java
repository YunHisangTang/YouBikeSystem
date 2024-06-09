package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.dto.RentBikeDTO;
import com.youbike.YouBikeSystemBackend.dto.ReturnBikeDTO;
import com.youbike.YouBikeSystemBackend.dto.StationDTO;
import com.youbike.YouBikeSystemBackend.model.*;
import com.youbike.YouBikeSystemBackend.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private DockRepository dockRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentalRecordRepository rentalRecordRepository;
    @Autowired
    private EasyCardRepository easyCardRepository;
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RentalRecordService rentalRecordService;

    public StationDTO rentBike(RentBikeDTO rentBikeDTO) {
        User user = userRepository.findByPhoneNumber(rentBikeDTO.getUserPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        EasyCard easyCard = easyCardRepository.findById(user.getEasyCardNumber())
                .orElseThrow(() -> new RuntimeException("EasyCard not found"));

        if (easyCard.getBalance() < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        Optional<RentalRecord> nowUserRentalRecord = rentalRecordService.getCurrentRentalRecord(easyCard.getCardNumber());
        if (nowUserRentalRecord.isPresent()) {
            throw new RuntimeException("The car you borrowed last time has not been returned. Please return it before renting another vehicle.");
        }
        Bike bike = bikeRepository.findByBikeUID(rentBikeDTO.getBikeUID())
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        if (!"Operational".equals(bike.getStatus())) {
            throw new RuntimeException("Bike is not available");
        }

        Dock dock = dockRepository.findByBikeUID(rentBikeDTO.getBikeUID())
                .orElseThrow(() -> new RuntimeException("Dock not found for this bike"));

        Station station = stationRepository.findByStationUID(dock.getStationUID())
                .orElseThrow(() -> new RuntimeException("Station not found"));

        // 更新自行车状态
        bike.setStatus("Rented");
        dock.setBikeUID(null);
        bikeRepository.save(bike);
        dockRepository.save(dock);

        // 生成租用记录
        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setUserId(user.getId());
        rentalRecord.setEasyCardNumber(easyCard.getCardNumber());
        rentalRecord.setBikeUID(bike.getBikeUID());
        rentalRecord.setRentStationUid(rentBikeDTO.getStationUID());
        rentalRecord.setRentStation(station.getNameZhTw());
        rentalRecord.setRentTime(rentBikeDTO.getStartTime());
        rentalRecordRepository.save(rentalRecord);

        return updateStationInfo(station);
    }

    public StationDTO returnBike(ReturnBikeDTO returnBikeDTO) {
        User user = userRepository.findByPhoneNumber(returnBikeDTO.getUserPhoneNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        EasyCard easyCard = easyCardRepository.findById(user.getEasyCardNumber())
                .orElseThrow(() -> new RuntimeException("EasyCard not found"));

        RentalRecord rentalRecord = rentalRecordRepository.findFirstByEasyCardNumberAndReturnTimeIsNullOrderByRentTimeDesc(easyCard.getCardNumber())
                .orElseThrow(() -> new RuntimeException("No active rental found"));

        Bike bike = bikeRepository.findByBikeUID(rentalRecord.getBikeUID())
                .orElseThrow(() -> new RuntimeException("Bike not found"));


        Dock availableDock = dockRepository.findFirstAvailableDock(returnBikeDTO.getStationUID())
                .orElseThrow(() -> new RuntimeException("No available dock found at the station"));


        Station station = stationRepository.findByStationUID(returnBikeDTO.getStationUID())
                .orElseThrow(() -> new RuntimeException("Station not found"));

        bike.setStatus("Operational");
        availableDock.setBikeUID(bike.getBikeUID());
        bikeRepository.save(bike);
        dockRepository.save(availableDock);

        rentalRecord.setReturnStationUid(returnBikeDTO.getStationUID());
        rentalRecord.setReturnStation(station.getNameZhTw());
        rentalRecord.setReturnTime(returnBikeDTO.getEndTime());

        Duration rentalDuration = Duration.between(rentalRecord.getRentTime(), rentalRecord.getReturnTime());
        double rentalAmount = calculateRentalAmount(rentalDuration);

        rentalRecord.setAmount(rentalAmount);
        rentalRecordRepository.save(rentalRecord);

        easyCard.setBalance(easyCard.getBalance() - rentalAmount);
        easyCardRepository.save(easyCard);

        return updateStationInfo(station);
    }

    private double calculateRentalAmount(Duration rentalDuration) {
        long minutes = rentalDuration.toMinutes();
        if (minutes <= 30) {
            return 5;
        } else if (minutes <= 60) {
            return 10;
        } else if (minutes <= 90) {
            return 15;
        } else if (minutes <= 120) {
            return 20;
        } else {
            return 20 + ((minutes - 120) / 30) * 10;
        }
    }

    private StationDTO updateStationInfo(Station station) {
        int availableBikes = (int) dockRepository.findByStationUID(station.getStationUID()).stream()
                .filter(d -> d.getBikeUID() != null && !d.getBikeUID().isEmpty()).count();
        int availableSpaces = (int) dockRepository.findByStationUID(station.getStationUID()).stream()
                .filter(d -> d.getBikeUID() == null || d.getBikeUID().isEmpty()).count();

        StationDTO stationDTO = new StationDTO();
        BeanUtils.copyProperties(station, stationDTO);
        stationDTO.setAvailableBikes(availableBikes);
        stationDTO.setAvailableSpaces(availableSpaces);

        return stationDTO;
    }

    public boolean checkUserRentalStatus(String easyCardNumber) {
        return rentalRecordRepository.findFirstByEasyCardNumberAndReturnTimeIsNullOrderByRentTimeDesc(easyCardNumber).isPresent();
    }

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }



    public Optional<Bike> findByBikeUID(String bikeUID) {
        return bikeRepository.findByBikeUID(bikeUID);
    }

    public Bike saveBike(Bike bike) {
        return bikeRepository.save(bike);
    }

}

package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.model.Dock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Query("SELECT b FROM Bike b WHERE b.bikeUID = :bikeUID AND b.status = :status")
    Bike findByBikeUIDAndStatus(@Param("bikeUID") String bikeUID, @Param("status") String status);


    Optional<Bike> findByBikeUID(String bikeUID);


}

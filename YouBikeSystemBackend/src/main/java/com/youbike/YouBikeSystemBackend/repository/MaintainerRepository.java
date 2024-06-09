package com.youbike.YouBikeSystemBackend.repository;

import com.youbike.YouBikeSystemBackend.model.Maintainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintainerRepository extends JpaRepository<Maintainer, Long> {
    Maintainer findByEmployeeId(String employeeId);
}

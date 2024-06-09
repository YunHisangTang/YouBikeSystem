package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.model.Maintainer;
import com.youbike.YouBikeSystemBackend.repository.MaintainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintainerService {

    @Autowired
    private MaintainerRepository maintainerRepository;

    public Maintainer findByEmployeeId(String employeeId) {
        return maintainerRepository.findByEmployeeId(employeeId);
    }

    public Maintainer saveMaintainer(Maintainer maintainer) {
        return maintainerRepository.save(maintainer);
    }
}

package com.youbike.YouBikeSystemBackend.service;

import com.youbike.YouBikeSystemBackend.dto.DockDTO;
import com.youbike.YouBikeSystemBackend.dto.StationDTO;
import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.model.Dock;
import com.youbike.YouBikeSystemBackend.model.Station;
import com.youbike.YouBikeSystemBackend.repository.BikeRepository;
import com.youbike.YouBikeSystemBackend.repository.DockRepository;
import com.youbike.YouBikeSystemBackend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private DockRepository dockRepository;

    @Autowired
    private BikeRepository bikeRepository;

    public List<StationDTO> searchStations(String name, Double lat, Double lon, Double range, Boolean hasBike, Boolean hasSpace, int page, int size) {

        if ((lon != null && (lat == null || range == null)) ||
                (lat != null && (lon == null || range == null)) ||
                (range != null && (lon == null || lat == null))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lon, lat, and range must all be provided together or all be null");
        }

        List<Station> stations;

        // Step 1: Filter by name
        if (name != null) {
            stations = stationRepository.findByNameZhTwContainingOrNameEnContaining(name);
        } else {
            stations = stationRepository.findAll();
        }

        // Step 2: Filter by location within distance
        if (lon != null && lat != null && range != null) {
            List<Station> locationFilteredStations = stationRepository.findByLocationWithinDistance(lat, lon, range);
            stations = stations.stream()
                    .filter(locationFilteredStations::contains)
                    .collect(Collectors.toList());
        }

        // Step 3: Filter by available bikes
        if (hasBike != null && hasBike) {
            List<Station> bikeFilteredStations = stationRepository.findByHasBikes();
            stations = stations.stream()
                    .filter(bikeFilteredStations::contains)
                    .collect(Collectors.toList());
        }

        // Step 4: Filter by available slots
        if (hasSpace != null && hasSpace) {
            List<Station> slotFilteredStations = stationRepository.findByHasSlots();
            stations = stations.stream()
                    .filter(slotFilteredStations::contains)
                    .collect(Collectors.toList());
        }

        // Pagination and return JSON result
        List<StationDTO> stationDTOs = stations.stream()
                .skip((long) page * size)
                .limit(size)
                .map(this::convertToDto)
                .collect(Collectors.toList());

        if (stationDTOs.isEmpty()) {
            return new ArrayList<>(); // 确保返回空的 JSON 数组
        }

        return stationDTOs;
    }

    private StationDTO convertToDto(Station station) {
        StationDTO stationDTO = new StationDTO();
        stationDTO.setId(station.getId());
        stationDTO.setStationUID(station.getStationUID());
        stationDTO.setStationID(station.getStationID());
        stationDTO.setAuthorityID(station.getAuthorityID());
        stationDTO.setNameZhTw(station.getNameZhTw());
        stationDTO.setNameEn(station.getNameEn());
        stationDTO.setPositionLon(station.getPositionLon());
        stationDTO.setPositionLat(station.getPositionLat());
        stationDTO.setAddressZhTw(station.getAddressZhTw());
        stationDTO.setAddressEn(station.getAddressEn());
        stationDTO.setBikesCapacity(station.getBikesCapacity());

        List<Dock> docks = dockRepository.findOperationalDocksByStationUID(station.getStationUID());
        List<DockDTO> dockDTOs = docks.stream()
                .map(this::convertToDockDto)
                .collect(Collectors.toList());

        int operationalDocksCount = 0;
        int operationalBikesCount = 0;
        Set<String> bikeTypes = new HashSet<>();

        for (Dock dock : docks) {
            if (dock.getBikeUID() != null && !dock.getBikeUID().isEmpty()) {
                Bike bike = bikeRepository.findByBikeUIDAndStatus(dock.getBikeUID(), "Operational");
                if (bike != null) {
                    operationalBikesCount++;
                    bikeTypes.add(bike.getType());
                }
            }
            operationalDocksCount++;
        }

        int availableSpaces = operationalDocksCount - operationalBikesCount;

        stationDTO.setAvailableSpaces(availableSpaces);
        stationDTO.setAvailableBikes(operationalBikesCount);
        stationDTO.setBikeTypes(bikeTypes.isEmpty() ? Collections.singletonList("None") : new ArrayList<>(bikeTypes));
        stationDTO.setDocks(dockDTOs);

        return stationDTO;
    }

    private DockDTO convertToDockDto(Dock dock) {
        DockDTO dockDTO = new DockDTO();
        dockDTO.setId(dock.getId());
        dockDTO.setStationUID(dock.getStationUID());
        dockDTO.setDockUID(dock.getDockUID());
        dockDTO.setDockID(dock.getDockID());
        dockDTO.setBikeUID(dock.getBikeUID());
        dockDTO.setStatus(dock.getStatus());
        dockDTO.setMaintenanceInfo(dock.getMaintenanceInfo());
        return dockDTO;
    }


    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    public Map<String, Integer> getStationInfo(String stationUID) {
        List<Dock> docks = dockRepository.findOperationalDocksByStationUID(stationUID);
        int availableBikes = (int) docks.stream().filter(dock -> dock.getBikeUID() != null && !dock.getBikeUID().isEmpty()).count();
        int availableSpaces = (int) docks.stream().filter(dock -> dock.getBikeUID() == null || dock.getBikeUID().isEmpty()).count();

        Map<String, Integer> info = new HashMap<>();
        info.put("availableBikes", availableBikes);
        info.put("availableSpaces", availableSpaces);

        return info;
    }
    public List<String> getBikesInStation(String stationUID) {
        return dockRepository.findOperationalDocksByStationUID(stationUID)
                .stream()
                .filter(dock -> dock.getBikeUID() != null && !dock.getBikeUID().isEmpty())
                .map(Dock::getBikeUID)
                .collect(Collectors.toList());
    }
    public void moveBikes(String sourceStationUID, String destinationStationUID, int numberOfBikes) {
        List<Dock> sourceDocks = dockRepository.findOperationalDocksByStationUID(sourceStationUID);
        List<Dock> destinationDocks = dockRepository.findOperationalDocksByStationUID(destinationStationUID);

        List<Dock> sourceBikeDocks = new ArrayList<>();
        for (Dock dock : sourceDocks) {
            if (dock.getBikeUID() != null && !dock.getBikeUID().isEmpty()) {
                sourceBikeDocks.add(dock);
            }
        }

        List<Dock> destinationEmptyDocks = new ArrayList<>();
        for (Dock dock : destinationDocks) {
            if (dock.getBikeUID() == null || dock.getBikeUID().isEmpty()) {
                destinationEmptyDocks.add(dock);
            }
        }

        if (sourceBikeDocks.size() < numberOfBikes || destinationEmptyDocks.size() < numberOfBikes) {
            throw new IllegalArgumentException("Not enough bikes or spaces available for the move");
        }

        for (int i = 0; i < numberOfBikes; i++) {
            Dock sourceDock = sourceBikeDocks.get(i);
            Dock destinationDock = destinationEmptyDocks.get(i);
            String bikeUID = sourceDock.getBikeUID();

            sourceDock.setBikeUID(null);
            dockRepository.save(sourceDock);

            destinationDock.setBikeUID(bikeUID);
            dockRepository.save(destinationDock);
        }
    }

    public Map<String, String> moveSpecificBike(String bikeUID, String destinationStationUID) {
        Optional<Dock> sourceDockOptional = dockRepository.findByBikeUID(bikeUID);
        if (!sourceDockOptional.isPresent()) {
            throw new IllegalArgumentException("Bike not found in any dock");
        }

        Dock sourceDock = sourceDockOptional.get();
        List<Dock> destinationDocks = dockRepository.findOperationalDocksByStationUID(destinationStationUID)
                .stream()
                .filter(dock -> dock.getBikeUID() == null || dock.getBikeUID().isEmpty())
                .collect(Collectors.toList());

        if (destinationDocks.isEmpty()) {
            throw new IllegalArgumentException("No available spaces in the destination station");
        }

        Dock destinationDock = destinationDocks.get(0);
        destinationDock.setBikeUID(sourceDock.getBikeUID());
        sourceDock.setBikeUID(null);

        dockRepository.save(sourceDock);
        dockRepository.save(destinationDock);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Bike moved successfully");

        return response;
    }
}

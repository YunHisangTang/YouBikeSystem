package com.youbike.YouBikeSystemBackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youbike.YouBikeSystemBackend.model.Bike;
import com.youbike.YouBikeSystemBackend.model.Dock;
import com.youbike.YouBikeSystemBackend.model.Station;
import com.youbike.YouBikeSystemBackend.repository.BikeRepository;
import com.youbike.YouBikeSystemBackend.repository.DockRepository;
import com.youbike.YouBikeSystemBackend.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Service
public class DataImportService {

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private DockRepository dockRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (bikeRepository.count() == 0 ) {
                importBikes();
            }
            if (stationRepository.count() == 0) {
                importStations();
            }
            if (dockRepository.count() == 0) {
                importDocks();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importBikes() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bikesNode = mapper.readTree(new File("D:\\Course\\OOPL\\Project\\YouBikeSystemBackend\\src\\main\\resources\\data\\Bikes.json"));
        Iterator<JsonNode> elements = bikesNode.elements();
        while (elements.hasNext()) {
            JsonNode bikeNode = elements.next();
            Bike bike = new Bike();
            bike.setBikeUID(bikeNode.get("BikeUID").asText());
            bike.setAuthorityID(bikeNode.get("AuthorityID").asText());
            bike.setType(bikeNode.get("Type").asText());
            bike.setStatus("Operational");
            bikeRepository.save(bike);
        }
    }

    private void importStations() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File folder = new File("D:\\Course\\OOPL\\Project\\YouBikeSystemBackend\\src\\main\\resources\\data\\stations"); // 存放多个Station JSON文件的文件夹
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                JsonNode stationsNode = mapper.readTree(file);
                Iterator<JsonNode> elements = stationsNode.elements();
                while (elements.hasNext()) {
                    JsonNode stationNode = elements.next();
                    Station station = new Station();
                    station.setStationUID(stationNode.get("StationUID").asText());
                    station.setStationID(stationNode.get("StationID").asText());
                    station.setAuthorityID(stationNode.get("AuthorityID").asText());
                    station.setNameZhTw(stationNode.get("StationName").get("Zh_tw").asText());
                    station.setNameEn(stationNode.get("StationName").get("En").asText());
                    station.setPositionLon(stationNode.get("StationPosition").get("PositionLon").asDouble());
                    station.setPositionLat(stationNode.get("StationPosition").get("PositionLat").asDouble());
                    station.setAddressZhTw(stationNode.get("StationAddress").get("Zh_tw").asText());
                    station.setAddressEn(stationNode.get("StationAddress").get("En").asText());
                    station.setBikesCapacity(stationNode.get("BikesCapacity").asInt());
                    stationRepository.save(station);
                }
            }
        }
    }

    private void importDocks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode docksNode = mapper.readTree(new File("D:\\Course\\OOPL\\Project\\YouBikeSystemBackend\\src\\main\\resources\\data\\Docks.json"));
        Iterator<JsonNode> elements = docksNode.elements();
        while (elements.hasNext()) {
            JsonNode dockNode = elements.next();
            Dock dock = new Dock();
            dock.setDockUID(dockNode.get("DockUID").asText());
            dock.setDockID(dockNode.get("DockID").asText());
            dock.setStationUID(dockNode.get("StationUID").asText());
            dock.setBikeUID(dockNode.get("Bike").asText(null));
            dock.setStatus("Operational");
            dockRepository.save(dock);
        }
    }
}

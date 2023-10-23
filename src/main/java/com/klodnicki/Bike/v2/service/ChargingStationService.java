package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;
    private final ModelMapper modelMapper;

    public ChargingStationService(ChargingStationRepository chargingStationRepository, ModelMapper modelMapper) {
        this.chargingStationRepository = chargingStationRepository;
        this.modelMapper = modelMapper;
    }

    public StationForAdminResponseDTO add(ChargingStation chargingStation) {
        ChargingStation chargingStation1 = chargingStationRepository.save(chargingStation);

        return modelMapper.map(chargingStation1, StationForAdminResponseDTO.class);
    }

    public List<StationForAdminResponseDTO> findAll() {
        Iterable<ChargingStation> chargingStations = chargingStationRepository.findAll();
        List<StationForAdminResponseDTO> stationsDTO = new ArrayList<>();

        for (ChargingStation station: chargingStations) {
            StationForAdminResponseDTO stationDTO = modelMapper.map(station, StationForAdminResponseDTO.class);
            stationsDTO.add(stationDTO);
        }
        return stationsDTO;
    }

    public StationForAdminResponseDTO findById(Long id) {
        ChargingStation chargingStation = chargingStationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(chargingStation, StationForAdminResponseDTO.class);
    }
}

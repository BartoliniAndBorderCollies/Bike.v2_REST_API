package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ChargingStationService(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    public StationForAdminResponseDTO add(ChargingStation chargingStation) {
        ChargingStation chargingStation1 = chargingStationRepository.save(chargingStation);

        return modelMapper.map(chargingStation1, StationForAdminResponseDTO.class);
    }

    public Iterable<ChargingStation> findAll() {
        return chargingStationRepository.findAll();
    }

    public StationForAdminResponseDTO findById(Long id) {
        ChargingStation chargingStation = chargingStationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(chargingStation, StationForAdminResponseDTO.class);
    }
}

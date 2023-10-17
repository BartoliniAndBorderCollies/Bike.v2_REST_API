package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingStationService {

    private final ChargingStationRepository chargingStationRepository;

    public ChargingStationService(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    public ChargingStation add(ChargingStation chargingStation) {
        return chargingStationRepository.save(chargingStation);
    }

    public Iterable<ChargingStation> findAll() {
        return chargingStationRepository.findAll();
    }

    public ChargingStation findById(Long id) {
        return chargingStationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}

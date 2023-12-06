package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.service.api.ChargingStationServiceApi;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChargingStationServiceHandler implements ChargingStationServiceApi {

    private final ChargingStationRepository chargingStationRepository;
    private final BikeServiceHandler bikeService;
    private final ModelMapper modelMapper;

    @Override
    public StationForAdminResponseDTO add(ChargingStation chargingStation) {
        ChargingStation chargingStation1 = chargingStationRepository.save(chargingStation);

        return modelMapper.map(chargingStation1, StationForAdminResponseDTO.class);
    }
    @Override
    public List<StationForAdminResponseDTO> findAll() {
        Iterable<ChargingStation> chargingStations = chargingStationRepository.findAll();
        List<StationForAdminResponseDTO> stationsDTO = new ArrayList<>();

        for (ChargingStation station: chargingStations) {
            StationForAdminResponseDTO stationDTO = modelMapper.map(station, StationForAdminResponseDTO.class);
            stationsDTO.add(stationDTO);
        }
        return stationsDTO;
    }
    @Override
    public StationForAdminResponseDTO findById(Long id) {
        ChargingStation chargingStation = chargingStationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(chargingStation, StationForAdminResponseDTO.class);
    }
    @Override
    public ChargingStation findStationById(Long id) throws NotFoundInDatabaseException {
        return chargingStationRepository.findById(id).orElseThrow(() -> new NotFoundInDatabaseException(ChargingStation.class));
    }
    @Override
    public ChargingStation save(ChargingStation chargingStation) {
        return chargingStationRepository.save(chargingStation);
    }

    @Override
    public ChargingStation addBikeToList(Long chargingStationId, Long bikeId) throws NotFoundInDatabaseException {
        ChargingStation chargingStation = findStationById(chargingStationId);
        Bike bike = bikeService.findBikeById(bikeId);

        bike.setChargingStation(chargingStation);
        chargingStation.getBikeList().add(bike);
        //In JPA, only the owning side of the relationship is used when writing to the database.
        //which means this will not be saved in database. Charging station still will have an empty bike list.
        //because ChargingStation is NOT the owning-side.
        return save(chargingStation);
    }
}

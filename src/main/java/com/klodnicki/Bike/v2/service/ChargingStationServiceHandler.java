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
    /**
     * This method is used to add a new charging station to the repository. It accepts a {@link ChargingStation} parameter.
     * First, it saves the chargingStation object in the repository.
     * Then, it maps the saved object to a {@link StationForAdminResponseDTO} and returns it.
     *
     * @param chargingStation The charging station to be added.
     * @return StationForAdminResponseDTO The response object containing the details of the added charging station.
     */
    @Override
    public StationForAdminResponseDTO add(ChargingStation chargingStation) {
        ChargingStation chargingStation1 = chargingStationRepository.save(chargingStation);

        return modelMapper.map(chargingStation1, StationForAdminResponseDTO.class);
    }
    /**
     * This method is used to retrieve all charging stations from the repository.
     * It fetches all {@link ChargingStation} objects, maps each one to a {@link StationForAdminResponseDTO}, and returns a list of these DTOs.
     *
     * @return List<StationForAdminResponseDTO> A list of response objects containing the details of all charging stations.
     */
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
    /**
     * This method is used to find a charging station by its ID.
     * It fetches the {@link ChargingStation} object associated with the given ID from the repository.
     * If no such object exists, it throws an IllegalArgumentException.
     * Then, it maps the fetched object to a {@link StationForAdminResponseDTO} and returns it.
     *
     * @param id The ID of the charging station to be found.
     * @return StationForAdminResponseDTO The response object containing the details of the found charging station.
     * @throws IllegalArgumentException If no charging station with the given ID is found.
     */
    @Override
    public StationForAdminResponseDTO findById(Long id) throws NotFoundInDatabaseException {
        ChargingStation chargingStation = chargingStationRepository.findById(id).orElseThrow(() ->
                new NotFoundInDatabaseException(ChargingStation.class));
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

    /**
     * This method is used to add a bike to the list of bikes in a charging station.
     * It first finds the charging station and the bike by their respective IDs.
     * Then, it sets the charging station for the bike and adds the bike to the charging station's bike list.
     * Finally, it saves the charging station and returns it.
     *
     * @param chargingStationId The ID of the charging station.
     * @param bikeId The ID of the bike to be added.
     * @return ChargingStation The updated charging station.
     * @throws IllegalArgumentException If no charging station or bike with the given IDs is found.
     */
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

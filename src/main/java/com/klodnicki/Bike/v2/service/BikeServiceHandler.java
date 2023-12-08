package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.service.api.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BikeServiceHandler implements BikeServiceApi {

    private final BikeRepository bikeRepository;
    private final ModelMapper modelMapper;

    /**
     * This method adds a new bike to the repository. It takes {@link BikeRequestDTO} as parameter, converts it to a bike object,
     * and save it in repo. Then again using modelMapper it converts it from  bike object into {@link BikeForAdminResponseDTO}
     * and returns it.
     *
     * @param bikeRequestDTO The BikeRequestDTO object containing the details of the bike to be added.
     * @return BikeForAdminResponseDTO Returns a BikeForAdminResponseDTO object containing the details of the saved bike.
     */
    @Override
    public BikeForAdminResponseDTO add(@NonNull BikeRequestDTO bikeRequestDTO) {
//        if (bikeRequestDTO == null) {
//            throw new NullPointerException();
//        }
        //using ModelMapper to convert(map) bikeRequestDTO into bike. I need Bike to be able to save it in repo
        Bike bike = modelMapper.map(bikeRequestDTO, Bike.class);
        Bike savedBike = bikeRepository.save(bike);
        //converting back to BikeForAdminResponseDTO using modelMapper
        BikeForAdminResponseDTO bikeDto = modelMapper.map(savedBike, BikeForAdminResponseDTO.class);

        return bikeDto;
    }

    /**
     * This method retrieves all bikes from the repository, converts each bike object into a {@link BikeForAdminResponseDTO} object,
     * and returns a list of these objects.
     *
     * @return List<BikeForAdminResponseDTO> Returns a list of BikeForAdminResponseDTO objects containing the details of all bikes in the repository.
     */
    @Override
    public List<BikeForAdminResponseDTO> findAll() {
        List<BikeForAdminResponseDTO> bikeListDto = new ArrayList<>();
        Iterable<Bike> bikeList = bikeRepository.findAll();

        for (Bike bike : bikeList) {

            BikeForAdminResponseDTO bikeDTO = modelMapper.map(bike, BikeForAdminResponseDTO.class);
            bikeListDto.add(bikeDTO);
        }

        return bikeListDto;
    }
    /**
     * This method retrieves a bike from the repository by its ID, converts the bike object into a {@link BikeForAdminResponseDTO}
     * object, and returns it.
     *
     * @param id The ID of the bike to be retrieved.
     * @return BikeForAdminResponseDTO Returns a BikeForAdminResponseDTO object containing the details of the retrieved bike.
     */
    @Override
    public BikeForAdminResponseDTO findById(Long id) throws NotFoundInDatabaseException {
        Bike savedBike = findBikeById(id);

        return modelMapper.map(savedBike, BikeForAdminResponseDTO.class);
    }

    /**
     * This method updates an existing bike in the repository. It takes the ID of the bike to be updated and a {@link BikeRequestDTO} object containing the updated details.
     * The method first retrieves the bike by its ID, then updates the bike's details if they are not null, and finally saves the updated bike in the repository.
     * The updated bike object is then converted into a {@link BikeForAdminResponseDTO} object and returned.
     *
     * @param id The ID of the bike to be updated.
     * @param updatedBikeRequestDTO The BikeRequestDTO object containing the updated details of the bike.
     * @return BikeForAdminResponseDTO Returns a BikeForAdminResponseDTO object containing the details of the updated bike.
     */
    @Override
    public BikeForAdminResponseDTO update(Long id, BikeRequestDTO updatedBikeRequestDTO) throws NotFoundInDatabaseException {

        Bike bike = findBikeById(id);

        updateBikeIfValuesAreNotNulls(updatedBikeRequestDTO, bike);
        bike.setRented(updatedBikeRequestDTO.isRented());
        bikeRepository.save(bike);

        //converting Bike into BikeForAdminResponseDTO using model mapper
        return modelMapper.map(bike, BikeForAdminResponseDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        bikeRepository.deleteById(id);
    }

    public List<Bike> findByIsRentedFalse() {
        return bikeRepository.findByIsRentedFalse();
    }

    @Override
    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }

    @Override
    public Bike findBikeById(Long id) throws NotFoundInDatabaseException {
        return bikeRepository.findById(id).orElseThrow(() -> new NotFoundInDatabaseException(Bike.class));
    }

    /**
     * This private method updates the details of a bike object if the values in the updatedBikeRequestDTO object are not null.
     * It checks serial number, bike type, and GPS coordinates in the updatedBikeRequestDTO object, and if present, updates these values in the bike object.
     *
     * @param updatedBikeRequestDTO The {@link BikeRequestDTO} object containing the updated details of the bike.
     * @param bike The {@link Bike} object to be updated.
     */
    private void updateBikeIfValuesAreNotNulls(BikeRequestDTO updatedBikeRequestDTO, Bike bike) {

        Optional.ofNullable(updatedBikeRequestDTO.getSerialNumber()).ifPresent(bike::setSerialNumber);
        Optional.ofNullable(updatedBikeRequestDTO.getBikeType()).ifPresent(bike::setBikeType);
        Optional.ofNullable(updatedBikeRequestDTO.getGpsCoordinates()).ifPresent(bike::setGpsCoordinates);
    }
}

package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//public class BikeService implements GenericService<BikeForAdminResponseDTO, Bike> {
//zamienilismy BikeService dependency wstrzykiwane w controllerze na GenericBikeService
//Żeby móc podstawić różne implementacje BikeService, nie tylko ten jeden konkretny BikeService. Jak zrobisz klasę
//        BetterBikeService możesz bez żadnych zmian w kontrolerze z niego korzystać od razu
public class BikeService implements GenericBikeService {

    private final BikeRepository bikeRepository;

    private final ModelMapper modelMapper;

    public BikeService(BikeRepository bikeRepository, ModelMapper modelMapper) {
        this.bikeRepository = bikeRepository;
        this.modelMapper = modelMapper;
    }

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

    @Override
    public BikeForAdminResponseDTO findById(Long id) {
        Bike savedBike = getBike(id);

        return modelMapper.map(savedBike, BikeForAdminResponseDTO.class);
    }

    public List<Bike> findByIsRentedFalse() {
        return bikeRepository.findByIsRentedFalse();
    }

    @Override
    public Bike save(Bike bike) {
        return bike;
    }

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

    @Override
    public void deleteById(Long id) {
        bikeRepository.deleteById(id);
    }

    @Override
    public BikeForAdminResponseDTO update(Long id, BikeRequestDTO updatedBikeRequestDTO) {

        Bike bike = getBike(id);

        updateBikeIfValuesAreNotNulls(updatedBikeRequestDTO, bike);
        bike.setRented(updatedBikeRequestDTO.isRented());
        if (updatedBikeRequestDTO.getAmountToBePaid() != 0) {
            bike.setAmountToBePaid(updatedBikeRequestDTO.getAmountToBePaid());
        }

        bikeRepository.save(bike);

        //converting Bike into BikeForAdminResponseDTO using model mapper
        return modelMapper.map(bike, BikeForAdminResponseDTO.class);
    }

    @Override
    public Bike getBike(Long id) {
        return bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    private void updateBikeIfValuesAreNotNulls(BikeRequestDTO updatedBikeRequestDTO, Bike bike) {

        Optional.ofNullable(updatedBikeRequestDTO.getSerialNumber()).ifPresent(bike::setSerialNumber);
        Optional.ofNullable(updatedBikeRequestDTO.getBikeType()).ifPresent(bike::setBikeType);
        Optional.ofNullable(updatedBikeRequestDTO.getGpsCoordinates()).ifPresent(bike::setGpsCoordinates);
    }
}

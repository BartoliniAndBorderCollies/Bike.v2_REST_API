package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
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

    ModelMapper modelMapper = new ModelMapper();

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public BikeForAdminResponseDTO add(BikeRequestDTO bikeRequestDTO) {
        //using ModelMapper to convert(map) bikeRequestDTO into bike. I need Bike to be able to save it in repo
        Bike bike = modelMapper.map(bikeRequestDTO, Bike.class);
        Bike savedBike = bikeRepository.save(bike);
        //converting back to BikeForAdminResponseDTO using modelMapper
        BikeForAdminResponseDTO bikeDto = modelMapper.map(savedBike, BikeForAdminResponseDTO.class);

        return bikeDto;
    }

    @Override
    public BikeForAdminResponseDTO findById(Long id) {
        Bike savedBike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new); //TODO: add custom exception

        return modelMapper.map(savedBike, BikeForAdminResponseDTO.class);
    }

    @Override
    public List<BikeForAdminResponseDTO> findAll() {
        List<BikeForAdminResponseDTO> bikeListDto = new ArrayList<>();
        Iterable<Bike> bikeList = bikeRepository.findAll();

        for (Bike bike : bikeList) {

            // Builder is in Lombok dependency
            // BikeForAdminResponseDTO must have @Builder annotation
            BikeForAdminResponseDTO bikeDTO = BikeForAdminResponseDTO.builder()
                    .id(bike.getId())
                    .serialNumber(bike.getSerialNumber())
                    .isRented(bike.isRented())
                    .bikeType(bike.getBikeType())
                    .rentalStartTime(bike.getRentalStartTime())
                    .rentalEndTime(bike.getRentalEndTime())
                    .amountToBePaid(bike.getAmountToBePaid())
                    .gpsCoordinates(bike.getGpsCoordinates())
                    .build();

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

        Bike bike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        updateBikeIfValuesAreNotNulls(updatedBikeRequestDTO, bike);
        bike.setRented(updatedBikeRequestDTO.isRented());
        if (updatedBikeRequestDTO.getAmountToBePaid() != 0) {
            bike.setAmountToBePaid(updatedBikeRequestDTO.getAmountToBePaid());
        }

        bikeRepository.save(bike);

        //converting Bike into BikeForAdminResponseDTO using model mapper
        return modelMapper.map(bike, BikeForAdminResponseDTO.class);
    }

    private static void updateBikeIfValuesAreNotNulls(BikeRequestDTO updatedBikeRequestDTO, Bike bike) {

        Optional.ofNullable(updatedBikeRequestDTO.getSerialNumber()).ifPresent(bike::setSerialNumber);
        Optional.ofNullable(updatedBikeRequestDTO.getBikeType()).ifPresent(bike::setBikeType);
        Optional.ofNullable(updatedBikeRequestDTO.getRentalStartTime()).ifPresent(bike::setRentalStartTime);
        Optional.ofNullable(updatedBikeRequestDTO.getRentalEndTime()).ifPresent(bike::setRentalEndTime);
        Optional.ofNullable(updatedBikeRequestDTO.getGpsCoordinates()).ifPresent(bike::setGpsCoordinates);
    }
}

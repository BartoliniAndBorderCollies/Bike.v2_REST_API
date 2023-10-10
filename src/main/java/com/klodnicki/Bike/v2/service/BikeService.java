package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        return convertBikeIntoBikeForAdminResponseDTO(savedBike);
    }

    @Override
    public BikeForAdminResponseDTO findById(Long id) {
        Bike savedBike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new); //TODO: add custom exception

        return convertBikeIntoBikeForAdminResponseDTO(savedBike);
    }

    @Override
    public List<BikeForAdminResponseDTO> findAll() {
        List<BikeForAdminResponseDTO> bikeListDto = new ArrayList<>();
        Iterable<Bike> bikeList = bikeRepository.findAll();

        for (Bike bike : bikeList) {

            Long id = bike.getId();
            String serialNumber = bike.getSerialNumber();
            boolean isRented = bike.isRented();
            BikeType type = bike.getBikeType();
            LocalDateTime rentalStartTime = bike.getRentalStartTime();
            LocalDateTime rentalEndTime = bike.getRentalEndTime();
            double toBePaid = bike.getAmountToBePaid();
            GpsCoordinates gps = bike.getGpsCoordinates();

            BikeForAdminResponseDTO bikeDto = new BikeForAdminResponseDTO(id, serialNumber, isRented, type, rentalStartTime,
                    rentalEndTime, toBePaid, gps);
            bikeListDto.add(bikeDto);
        }

        return bikeListDto;
    }

    @Override
    public void deleteById(Long id) {
        bikeRepository.deleteById(id);
    }

    @Override
    public BikeForAdminResponseDTO update(Long id, BikeRequestDTO updatedBikeRequestDTO) {

        //converting updatedBikeRequestDTO into Bike using mapper (to be able to save it in repo)
        Bike bike = modelMapper.map(updatedBikeRequestDTO, Bike.class);

        if (updatedBikeRequestDTO.getSerialNumber() != null) {
            bike.setSerialNumber(updatedBikeRequestDTO.getSerialNumber());
        }

        bike.setRented(updatedBikeRequestDTO.isRented());

        if (updatedBikeRequestDTO.getBikeType() != null) {
            bike.setBikeType(updatedBikeRequestDTO.getBikeType());
        }
        if (updatedBikeRequestDTO.getRentalStartTime() != null) {
            bike.setRentalStartTime(updatedBikeRequestDTO.getRentalStartTime());
        }
        if (updatedBikeRequestDTO.getRentalEndTime() != null) {
            bike.setRentalEndTime(updatedBikeRequestDTO.getRentalEndTime());
        }
        if (updatedBikeRequestDTO.getAmountToBePaid() != 0) {
            bike.setAmountToBePaid(updatedBikeRequestDTO.getAmountToBePaid());
        }
        if (updatedBikeRequestDTO.getGpsCoordinates() != null) {
            bike.setGpsCoordinates(updatedBikeRequestDTO.getGpsCoordinates());
        }

        bikeRepository.save(bike);

        //converting Bike into BikeForAdminResponseDTO using builder
        return convertBikeIntoBikeForAdminResponseDTO(bike);
    }

    private static BikeForAdminResponseDTO convertBikeIntoBikeForAdminResponseDTO(Bike savedBike) {
        // Builder is in Lombok dependency
        // BikeForAdminResponseDTO must have @Builder annotation
        BikeForAdminResponseDTO bikeDTO = BikeForAdminResponseDTO.builder()
                .id(savedBike.getId())
                .serialNumber(savedBike.getSerialNumber())
                .isRented(savedBike.isRented())
                .bikeType(savedBike.getBikeType())
                .rentalStartTime(savedBike.getRentalStartTime())
                .rentalEndTime(savedBike.getRentalEndTime())
                .amountToBePaid(savedBike.getAmountToBePaid())
                .gpsCoordinates(savedBike.getGpsCoordinates())
                .build();
        return bikeDTO;
    }

}

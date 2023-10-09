package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BikeService implements GenericService<BikeForAdminResponseDTO, Bike> {

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Override
    public BikeForAdminResponseDTO add(BikeRequestDTO bikeRequestDTO) {

        //using ModelMapper to convert(map) bikeRequestDTO into bike
        ModelMapper modelMapper = new ModelMapper();
        Bike bike = modelMapper.map(bikeRequestDTO, Bike.class);


        Bike savedBike = bikeRepository.save(bike);

        BikeForAdminResponseDTO bikeDTO = new BikeForAdminResponseDTO(savedBike.getSerialNumber(), savedBike.isRented(),
                savedBike.getBikeType(), savedBike.getRentalStartTime(), savedBike.getRentalEndTime(),
                savedBike.getAmountToBePaid(), savedBike.getGpsCoordinates());

        return bikeDTO;
    }

    @Override
    public BikeForAdminResponseDTO findById(Long id) {
        Bike savedBike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new); //TODO: add custom exception

        BikeForAdminResponseDTO bikeDTO = new BikeForAdminResponseDTO(savedBike.getSerialNumber(), savedBike.isRented(),
                savedBike.getBikeType(), savedBike.getRentalStartTime(), savedBike.getRentalEndTime(),
                savedBike.getAmountToBePaid(), savedBike.getGpsCoordinates());

        return bikeDTO;
    }

    @Override
    public List<BikeForAdminResponseDTO> findAll() {
        List<BikeForAdminResponseDTO> bikeListDto = new ArrayList<>();
        List<Bike> bikeList = (List<Bike>) bikeRepository.findAll();

        for (Bike bike : bikeList) {

            String serialNumber = bike.getSerialNumber();
            boolean isRented = bike.isRented();
            BikeType type = bike.getBikeType();
            LocalDateTime rentalStartTime = bike.getRentalStartTime();
            LocalDateTime rentalEndTime = bike.getRentalEndTime();
            double toBePaid = bike.getAmountToBePaid();
            String gps = bike.getGpsCoordinates();

            BikeForAdminResponseDTO bikeDto = new BikeForAdminResponseDTO(serialNumber, isRented, type, rentalStartTime,
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
    public BikeForAdminResponseDTO update(Long id, Bike updatedBike) {
        Bike savedBike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);//TODO: make custom exception

        savedBike.setSerialNumber(updatedBike.getSerialNumber());
        savedBike.setRented(updatedBike.isRented());
        savedBike.setBikeType(updatedBike.getBikeType());
        savedBike.setRentalStartTime(updatedBike.getRentalStartTime());
        savedBike.setRentalEndTime(updatedBike.getRentalEndTime());
        savedBike.setAmountToBePaid(updatedBike.getAmountToBePaid());
        savedBike.setGpsCoordinates(updatedBike.getGpsCoordinates());

        bikeRepository.save(savedBike);

        BikeForAdminResponseDTO bikeDto = new BikeForAdminResponseDTO(savedBike.getSerialNumber(), savedBike.isRented(),
                savedBike.getBikeType(), savedBike.getRentalStartTime(), savedBike.getRentalEndTime(),
                savedBike.getAmountToBePaid(), savedBike.getGpsCoordinates());

        return bikeDto;
    }
}

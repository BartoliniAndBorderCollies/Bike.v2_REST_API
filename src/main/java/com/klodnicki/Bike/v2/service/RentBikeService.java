package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.RentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class RentBikeService implements RentBikeGenericService{

    private final BikeRepository bikeRepository;

    private final RentRepository rentRepository;

    ModelMapper modelMapper = new ModelMapper();

    public RentBikeService(BikeRepository bikeRepository, RentRepository rentRepository) {
        this.bikeRepository = bikeRepository;
        this.rentRepository = rentRepository;
    }


    @Override
    public BikeForNormalUserResponseDTO add(BikeRequestDTO obj) {
        return null;
    }

    @Override
    public BikeForNormalUserResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BikeForNormalUserResponseDTO update(Long id, BikeRequestDTO obj) {
        return null;
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        List<Bike> availableBikes = bikeRepository.findByIsRentedFalse();
        List<BikeForNormalUserResponseDTO> bikesForNormalUserDTO = new ArrayList<>();

        for (Bike bike: availableBikes) {

            BikeForNormalUserResponseDTO bikeForNormalDTO = BikeForNormalUserResponseDTO.builder()
                    .id(bike.getId())
                    .serialNumber(bike.getSerialNumber())
                    .isRented(bike.isRented())
                    .bikeType(bike.getBikeType())
                    .rentalStartTime(bike.getRentalStartTime())
                    .rentalEndTime(bike.getRentalEndTime())
                    .build();

            bikesForNormalUserDTO.add(bikeForNormalDTO);

        }
        return bikesForNormalUserDTO;
    }

    @Override
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id) {
        Bike bike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(bike, BikeForNormalUserResponseDTO.class);
    }



}

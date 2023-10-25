package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.service.crudInterface.AddService;
import com.klodnicki.Bike.v2.service.crudInterface.DeleteService;
import com.klodnicki.Bike.v2.service.crudInterface.FindService;
import com.klodnicki.Bike.v2.service.crudInterface.UpdateService;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService implements GenericBikeService, AddService<BikeForAdminResponseDTO, BikeRequestDTO>,
        FindService<BikeForAdminResponseDTO>, UpdateService<BikeForAdminResponseDTO, BikeRequestDTO>, DeleteService {

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
    public BikeForAdminResponseDTO findById(Long id) {
        Bike savedBike = findBikeById(id);

        return modelMapper.map(savedBike, BikeForAdminResponseDTO.class);
    }

    @Override
    public BikeForAdminResponseDTO update(Long id, BikeRequestDTO updatedBikeRequestDTO) {

        Bike bike = findBikeById(id);

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
    public void deleteById(Long id) {
        bikeRepository.deleteById(id);
    }

    public List<Bike> findByIsRentedFalse() {
        return bikeRepository.findByIsRentedFalse();
    }

    @Override
    public Bike save(Bike bike) {
        return bike;
    }

    @Override
    public Bike findBikeById(Long id) {
        return bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    private void updateBikeIfValuesAreNotNulls(BikeRequestDTO updatedBikeRequestDTO, Bike bike) {

        Optional.ofNullable(updatedBikeRequestDTO.getSerialNumber()).ifPresent(bike::setSerialNumber);
        Optional.ofNullable(updatedBikeRequestDTO.getBikeType()).ifPresent(bike::setBikeType);
        Optional.ofNullable(updatedBikeRequestDTO.getGpsCoordinates()).ifPresent(bike::setGpsCoordinates);
    }
}

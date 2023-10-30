package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BikeServiceHandlerTest {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BikeServiceHandler bikeServiceHandler;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void add_ShouldAddToDatabase_WhenGivenCorrectArguments() {
        //given
        bikeRepository.deleteAll();
        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO();
        bikeServiceHandler.add(bikeRequestDTO);
        Long expected = 1L;

        //when
        Long actual = bikeRepository.count();

        //then
        assertEquals(expected, actual);
    }
}
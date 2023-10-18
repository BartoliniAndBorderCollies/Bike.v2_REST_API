package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.repository.RentRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rent")
public class RentController {

    public final RentRepository rentRepository;

    public RentController(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    @DeleteMapping ("/{id}")
    public void deleteById(@PathVariable Long id) {
        rentRepository.deleteById(id);
    }
}

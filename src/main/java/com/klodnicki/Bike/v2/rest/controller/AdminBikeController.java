package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.service.api.BikeServiceApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bikes")
@AllArgsConstructor
public class AdminBikeController {

//zamienilismy BikeServiceHandler dependency wstrzykiwane w controllerze na BikeServiceApi
//Żeby móc podstawić różne implementacje BikeServiceHandler, nie tylko ten jeden konkretny BikeServiceHandler. Jak zrobisz klasę
//        BetterBikeService możesz bez żadnych zmian w kontrolerze z niego korzystać od razu

    private final BikeServiceApi bikeService;

    @PostMapping("/add")
    public BikeForAdminResponseDTO addBike(@RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.add(bikeDTO);
    }

    @GetMapping("/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @GetMapping
    public ListBikesForAdminResponseDTO findAllBikes() {
        List<BikeForAdminResponseDTO> listBikesDTO = bikeService.findAll();

        return new ListBikesForAdminResponseDTO(listBikesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.update(id, bikeDTO);
    }
}

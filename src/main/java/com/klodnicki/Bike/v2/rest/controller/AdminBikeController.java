package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.service.api.BikeServiceApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing bikes in the admin context.
 * It provides endpoints for adding, finding, deleting and updating bikes.
 */
@RestController
@RequestMapping("/api/admin/bikes")
@AllArgsConstructor
public class AdminBikeController {

//zamienilismy BikeServiceHandler dependency wstrzykiwane w controllerze na BikeServiceApi
//Żeby móc podstawić różne implementacje BikeServiceHandler, nie tylko ten jeden konkretny BikeServiceHandler. Jak zrobisz klasę
//        BetterBikeService możesz bez żadnych zmian w kontrolerze z niego korzystać od razu

    /**
     * Service for handling bike-related operations.
     */
    private final BikeServiceApi bikeService;

    /**
     * This method is used to add a new bike.
     * It takes a BikeRequestDTO object as input and returns a BikeForAdminResponseDTO object.
     *
     * @param bikeDTO This is a request object which contains the details of the bike to be added.
     * @return BikeForAdminResponseDTO This returns the response object with details of the added bike.
     *
     * @PostMapping("/add")
     */
    @PostMapping("/add")
    public BikeForAdminResponseDTO addBike(@Valid @RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.add(bikeDTO);
    }

    @GetMapping("/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) throws NotFoundInDatabaseException {
        return bikeService.findById(id);
    }

    /**
     * This method is used to retrieve all bikes.
     * It returns a ListBikesForAdminResponseDTO object which contains a list of BikeForAdminResponseDTO objects.
     *
     * @return ListBikesForAdminResponseDTO This returns the response object with a list of all bikes.
     *
     * @GetMapping
     */
    @GetMapping
    public ListBikesForAdminResponseDTO findAllBikes() {
        List<BikeForAdminResponseDTO> listBikesDTO = bikeService.findAll();

        return new ListBikesForAdminResponseDTO(listBikesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    /**
     * This method is used to update a bike by its ID.
     * It takes a BikeRequestDTO object and an ID of Bike as input and returns a BikeForAdminResponseDTO object.
     * BikeRequestDTO must fulfil some specific requirements since it is preceded by @Valid.
     *
     * @param id This is the ID of the bike to be updated.
     * @param bikeDTO This is a request object which contains the updated details of the bike.
     * @return BikeForAdminResponseDTO This returns the response object with details of the updated bike.
     *
     * @PutMapping("/{id}")
     */
    @PutMapping("/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @Valid @RequestBody BikeRequestDTO bikeDTO)
            throws NotFoundInDatabaseException {
        return bikeService.update(id, bikeDTO);
    }
}

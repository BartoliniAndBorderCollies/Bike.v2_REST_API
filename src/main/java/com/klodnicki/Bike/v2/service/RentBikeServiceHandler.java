package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.authority.AuthorityDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.*;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.service.api.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class handles the services related to renting bikes.
 * It implements the RentBikeServiceApi interface.
 */
@Service
@AllArgsConstructor
public class RentBikeServiceHandler implements RentBikeServiceApi {

    private final BikeServiceApi bikeService;
    private final ChargingStationServiceApi chargingStationService;
    private final UserServiceApi userService;
    private final ModelMapper modelMapper;
    private final RentRepository rentRepository;

    /**
     * This method is used to update the number of days of rent for a specific rent object.
     * It first finds the rent object by its ID.
     * If the number of days of rent in the request is greater than 0, it updates the rent object's days of rent.
     * Then, it saves the updated rent object in the repository, maps it and return {@link RentResponseDTO}.
     *
     * @param id The ID of the rent to be updated.
     * @param rentRequestDTO The {@link RentRequestDTO} object containing the updated number of days of rent.
     * @return RentResponseDTO The response object containing the details of the updated rent.
     * @throws NotFoundInDatabaseException If no rent with the given ID is found.
     */
    @Override
    public RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO) throws NotFoundInDatabaseException {
        Rent rent = findRentById(id);

        if (rentRequestDTO.getDaysOfRent() > 0) {
            rent.setDaysOfRent(rentRequestDTO.getDaysOfRent());
        }
        rentRepository.save(rent);
        return modelMapper.map(rent, RentResponseDTO.class);
    }

    /**
     * This method is used to find all available bikes.
     * It fetches all {@link Bike} objects that are not currently rented, and maps each one to a {@link BikeForNormalUserResponseDTO}.
     * It then returns a list of these DTOs.
     *
     * @return List<BikeForNormalUserResponseDTO> A list of response objects containing the details of all available bikes.
     */
    @Override
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        List<Bike> availableBikes = bikeService.findByIsRentedFalse();
        List<BikeForNormalUserResponseDTO> bikesForNormalUserDTO = new ArrayList<>();

        for (Bike bike : availableBikes) {

            BikeForNormalUserResponseDTO bikeForNormalDTO = BikeForNormalUserResponseDTO.builder()
                    .id(bike.getId())
                    .serialNumber(bike.getSerialNumber())
                    .isRented(bike.isRented())
                    .bikeType(bike.getBikeType())
                    .build();

            bikesForNormalUserDTO.add(bikeForNormalDTO);

        }
        return bikesForNormalUserDTO;
    }

    /**
     * This method is used to find a bike by its ID for a normal user.
     * It fetches the {@link Bike} object associated with the given ID from the service.
     * Then, it maps the fetched object to a {@link BikeForNormalUserResponseDTO} and returns it.
     *
     * @param id The ID of the bike to be found.
     * @return BikeForNormalUserResponseDTO The response object containing the details of the found bike.
     * @throws NotFoundInDatabaseException If no bike with the given ID is found.
     */
    @Override
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id) throws NotFoundInDatabaseException {
        Bike bike = bikeService.findBikeById(id);
        return modelMapper.map(bike, BikeForNormalUserResponseDTO.class);
    }
    /**
     * This method is used to rent a bike.
     * It first finds the bike, user, and charging station by their respective IDs.
     * Then, it sets the bike as rented, removes it from its charging station, and assigns it to the user.
     * It also updates the charging station's free slots and creates a new {@link Rent} object.
     * Finally, it saves the Rent object in the repository and returns a {@link RentResponseDTO} containing the details of the rent.
     *
     * @param rentRequest The request object containing the IDs of the bike and user, and the number of days of rent.
     * @return RentResponseDTO The response object containing the details of the rent.
     * @throws NotFoundInDatabaseException If no bike, user, or charging station with the given IDs is found.
     */
    @Override
    @Transactional
    public RentResponseDTO rent(RentRequest rentRequest) throws NotFoundInDatabaseException {
        Bike bike = bikeService.findBikeById(rentRequest.getBikeId());
        User user = userService.findUserById(rentRequest.getUserId());
        ChargingStation chargingStation = chargingStationService.findStationById(bike.getChargingStation().getId());

        bike.setRented(true);
        bike.setChargingStation(null);
        bike.setUser(user); //it is a relation @OneToOne therefore setting should be on both sides of owning
        user.setBike(bike);
        if (chargingStation.getBikeList() != null && !chargingStation.getBikeList().isEmpty()) {
            chargingStation.getBikeList().remove(bike);
        }
        chargingStation.setFreeSlots(chargingStation.getFreeSlots() + 1);
        int daysOfRent = rentRequest.getDaysOfRent();

        Rent rent = new Rent(null, LocalDateTime.now(), null, daysOfRent, 0.0, bike, user,
                null);
        rentRepository.save(rent);

        return new RentResponseDTO(rent.getId(), rent.getRentalStartTime(), null, rent.getDaysOfRent(),
                prepareBikeDTO(rent), prepareUserDTO(rent),null);
    }
    /**
     * This method is used to prepare a {@link UserForNormalUserResponseDTO} from a {@link Rent} object.
     * It extracts the user details from the Rent object and uses Builder to build a UserForNormalUserResponseDTO.
     *
     * @param rent The Rent object from which the user details are to be extracted.
     * @return UserForNormalUserResponseDTO The response object containing the details of the user.
     */
    private UserForNormalUserResponseDTO prepareUserDTO (Rent rent) {
        Set<AuthorityDTO> authorityDTOs = new HashSet<>();
        for (GrantedAuthority grantedAuthority : rent.getUser().getAuthorities()) {
            AuthorityDTO authorityDTO = modelMapper.map(grantedAuthority, AuthorityDTO.class);
            authorityDTOs.add(authorityDTO);
        }
        UserForNormalUserResponseDTO userDTO = UserForNormalUserResponseDTO.builder()
                .id(rent.getUser().getId())
                .name(rent.getUser().getName())
                .isAccountValid(rent.getUser().isAccountValid())
                .authorities(authorityDTOs)
                .build();
        return userDTO;
    }

    /**
     * This method prepares a {@link BikeForNormalUserResponseDTO} object from a given Rent object.
     * It takes the bike details from the {@link Rent} object and using Builder builds BikeForNormalUserResponseDTO.
     *
     * @param rent The Rent object containing the rental details.
     * @return A BikeForNormalUserResponseDTO object containing the bike details and rental times.
     */
    private BikeForNormalUserResponseDTO prepareBikeDTO(Rent rent) {
        BikeForNormalUserResponseDTO bikeDTO = BikeForNormalUserResponseDTO.builder()
                .id(rent.getBike().getId())
                .serialNumber(rent.getBike().getSerialNumber())
                .isRented(rent.getBike().isRented())
                .bikeType(rent.getBike().getBikeType())
                .rentalStartTime(rent.getRentalStartTime())
                .rentalEndTime(rent.getRentalEndTime())
                .build();

        return bikeDTO;
    }

    /**
     * This method handles the return of a rented vehicle. It takes the ID of the {@link Rent} and the ID of the {@link ChargingStation} where the vehicle is being returned as parameters.
     * It then retrieves the Rent from the repository and uses it to obtain the {@link Bike} and {@link User} objects.
     * Finally, it finds the return ChargingStation using its ID.
     *
     * The method then uses a private function to calculate the rental cost for the specific Rent. It sets the rental end time to the current time, sets the amount to be paid for the Rent, and assigns the returning ChargingStation to the Rent.
     *
     * The Bike is then marked as not rented and its associated User is set to null.
     * The returned Bike is added to the bike list of the ChargingStation where it was returned.
     * The number of free slots at the ChargingStation is decreased by one and the User's balance is reduced by the rental cost.
     *
     * Finally, these changes are saved to the database and a ResponseEntity is returned with a message indicating that the Bike was successfully returned.
     *
     * @param rentId The ID of the Rent object associated with the rented vehicle.
     * @param returnChargingStationId The ID of the ChargingStation where the vehicle is being returned.
     * @return A ResponseEntity with a success message and HTTP status code.
     * @throws NotFoundInDatabaseException if a bike, rent, user or charging station is not found in database.
     */
    @Override
    @Transactional
    public ResponseEntity<?> returnVehicle(Long rentId, Long returnChargingStationId) throws NotFoundInDatabaseException {
        Rent rent = findRentById(rentId);
        Bike bike = rent.getBike();
        User user = rent.getUser();
        ChargingStation returnChargingStation = chargingStationService.findStationById(returnChargingStationId);

        double rentalCost = countRentalCost(rentId);
        rent.setRentalEndTime(LocalDateTime.now());
        rent.setAmountToBePaid(rentalCost);
        rent.setChargingStation(returnChargingStation);

        bike.setRented(false);
        bike.setUser(null);

        returnChargingStation.getBikeList().add(bike); //Bike is an owning side, so I must add below line to save it in db
        bike.setChargingStation(returnChargingStation);

        returnChargingStation.setFreeSlots(returnChargingStation.getFreeSlots() - 1);

        user.setBalance(user.getBalance() - rentalCost); //reducing the user balance by rental cost

        bikeService.save(bike);
        chargingStationService.save(returnChargingStation);
        userService.save(user);
        rentRepository.save(rent);

        return new ResponseEntity<>("Bike successfully returned.", HttpStatus.OK);
    }

    /**
     * This method calculates the rental cost for a given {@link Rent} object.
     * It first retrieves the Rent object using its ID and checks the number of rental days.
     * If the user has chosen to rent by days, it returns the number of rental days times the daily rate (10).
     * If the user has chosen to rent by minutes, it calculates the duration of the rent in minutes and returns duration the per-minute rate (0.1).
     *
     * @param rentId The ID of the Rent object for which the rental cost is to be calculated.
     * @return The calculated rental cost.
     * @throws NotFoundInDatabaseException if rent is not found in database.
     */
    private double countRentalCost(Long rentId) throws NotFoundInDatabaseException {
        Rent rent = findRentById(rentId);
        int rentalDays = rent.getDaysOfRent();

        //option 1: if user chooses rent option for days
        if(rentalDays >0) {
            return rentalDays * 10;
        }

        //option 2: if user decides to rent per minutes
        long durationInMinutes = Duration.between(rent.getRentalStartTime(), rent.getRentalEndTime()).toMinutes();
        return durationInMinutes * 0.1;
    }

    private Rent findRentById(Long id) throws NotFoundInDatabaseException {
        return rentRepository.findById(id).orElseThrow(() -> new NotFoundInDatabaseException(Rent.class));
    }
}

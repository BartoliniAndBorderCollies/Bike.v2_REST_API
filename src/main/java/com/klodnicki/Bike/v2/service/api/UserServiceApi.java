package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.api.operation.AddOperation;
import com.klodnicki.Bike.v2.service.api.operation.DeleteOperation;
import com.klodnicki.Bike.v2.service.api.operation.FindOperation;
import com.klodnicki.Bike.v2.service.api.operation.SaveOperation;
import org.springframework.http.ResponseEntity;

/**
 * This interface defines the operations for the UserService.
 * It extends AddOperation, SaveOperation, FindOperation, and DeleteOperation interfaces.
 */
public interface UserServiceApi extends AddOperation<UserForAdminResponseDTO, User>,
        SaveOperation<User>, FindOperation<UserForAdminResponseDTO, Long>, DeleteOperation<Long> {

    User findUserById(Long id) throws NotFoundInDatabaseException;
    /**
     * This method is used to ban a user by their ID.
     * @param id This is the ID of the user to be banned.
     * @return ResponseEntity This returns the response entity after the user has been banned.
     * @throws NotFoundInDatabaseException if no user is found in database.
     */
    ResponseEntity<?> banUser(Long id) throws NotFoundInDatabaseException;
}

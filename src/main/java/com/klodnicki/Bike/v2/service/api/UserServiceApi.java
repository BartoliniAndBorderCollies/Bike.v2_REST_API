package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.api.operation.AddOperation;
import com.klodnicki.Bike.v2.service.api.operation.DeleteOperation;
import com.klodnicki.Bike.v2.service.api.operation.FindOperation;
import com.klodnicki.Bike.v2.service.api.operation.SaveOperation;
import org.springframework.http.ResponseEntity;

public interface UserServiceApi extends AddOperation<UserForAdminResponseDTO, User>,
        SaveOperation<User>, FindOperation<UserForAdminResponseDTO, Long>, DeleteOperation<Long> {

    User findUserById(Long id);
    ResponseEntity<?> banUser(Long id);
}

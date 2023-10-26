package com.klodnicki.Bike.v2.service.interfacee;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.AddService;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.DeleteService;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.FindService;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.SaveService;
import org.springframework.http.ResponseEntity;

public interface GenericUserService extends AddService<UserForAdminResponseDTO, User>,
        SaveService<User>, FindService<UserForAdminResponseDTO, Long>, DeleteService<Long> {

    User findUserById(Long id);
    ResponseEntity<?> banUser(Long id);
}

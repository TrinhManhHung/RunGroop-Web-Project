package com.rungroop.web.service;

import com.rungroop.web.dto.RegistrationDto;
import com.rungroop.web.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;


public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(@NotEmpty String email);

    UserEntity findByUsername(@NotEmpty String username);
}

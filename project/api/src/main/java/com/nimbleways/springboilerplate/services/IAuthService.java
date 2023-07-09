package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.dto.LoginRequestDTO;
import com.nimbleways.springboilerplate.dto.LoginResponseDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.entities.User;

public interface IAuthService {
    User createUser(UserDTO user, String password);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.dto.UpdateUserRequestDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.exceptions.UserNotFoundException;

import java.util.UUID;

public interface IUserService {
    UserDTO getUser(String id) throws UserNotFoundException;
    UserDTO updateUser(UUID id, UpdateUserRequestDTO user);
}

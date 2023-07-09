package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.dto.UpdateUserRequestDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.entities.User;
import com.nimbleways.springboilerplate.exceptions.UserNotFoundException;
import com.nimbleways.springboilerplate.mappers.UserMapper;
import com.nimbleways.springboilerplate.repositories.UserRepository;
import com.nimbleways.springboilerplate.services.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository,final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO getUser(String id) throws UserNotFoundException {
        final User user = userRepository.findById(UUID.fromString(id))
                            .orElseThrow(()->new UserNotFoundException(id));
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(UUID id, UpdateUserRequestDTO user) throws UserNotFoundException{
        User userEntity = userRepository.findById(id)
                            .orElseThrow(()->new UserNotFoundException(id.toString()));

        if(!user.getPassword().isBlank()) {
            String newPassword = passwordEncoder.encode(user.getPassword());
            userEntity.setPassword(newPassword);
        }
        userEntity.setShouldReceiveMailNotifications(user.isShouldReceiveMailNotification());
        userEntity.setShouldReceiveApprovalNotifications(user.isShouldReceiveApprovalNotifications());

        userEntity = userRepository.save(userEntity);

        return UserMapper.INSTANCE.userToUserDTO(userEntity);
    }
}

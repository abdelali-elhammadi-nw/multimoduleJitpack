package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.dto.LoginRequestDTO;
import com.nimbleways.springboilerplate.dto.LoginResponseDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.entities.User;
import com.nimbleways.springboilerplate.exceptions.UserAlreadyExistsException;
import com.nimbleways.springboilerplate.exceptions.UserNotFoundException;
import com.nimbleways.springboilerplate.mappers.UserMapper;
import com.nimbleways.springboilerplate.repositories.UserRepository;
import com.nimbleways.springboilerplate.security.JwtTokenUtil;
import com.nimbleways.springboilerplate.services.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final UserRepository userRepository, final AuthenticationManager authenticationManager, final JwtTokenUtil jwtTokenUtils, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(final LoginRequestDTO loginRequest) {
        final User user = userRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(()->new UserNotFoundException(loginRequest.getEmail()));

        log.info(String.format("Login attempt for user with id: %s", user.getId()));

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtTokenUtils.generateAccessToken(user);
        log.info(String.format("Login attempt successful for user with id: %s", user.getId()));

        final UserDTO userDto = UserMapper.INSTANCE.userToUserDTO(user);

        return new LoginResponseDTO(jwt,userDto);
    }

    @Override
    public User createUser(final UserDTO userDTO, final String password) {
        final Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if( userOptional.isPresent() ){
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }

        final User user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        final LocalDateTime createdAt = LocalDateTime.now();
        user.setCreatedAt(createdAt);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}

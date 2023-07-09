package com.nimbleways.springboilerplate.contollers;

import com.nimbleways.springboilerplate.dto.LoginRequestDTO;
import com.nimbleways.springboilerplate.dto.LoginResponseDTO;
import com.nimbleways.springboilerplate.dto.SignupRequestDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.entities.User;
import com.nimbleways.springboilerplate.mappers.UserMapper;
import com.nimbleways.springboilerplate.services.IAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(final IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid final SignupRequestDTO signupRequestDTO) {
        final UserDTO userDTO = UserMapper.INSTANCE.signupRequestDtoToUserDTO(signupRequestDTO);
        final User user = authService.createUser(userDTO, signupRequestDTO.getPassword());
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @PostMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid final LoginRequestDTO loginRequestDTO) {
        final LoginResponseDTO loginResponseDto = authService.login(loginRequestDTO);
        // TODO : remove hardcoding of Bearer
        final String token = loginResponseDto.getJwtToken();
        final int duration = 7 * 24 * 60 * 60;
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,"token="+token+"; Max-Age="+duration+"; Path=/; HttpOnly");
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(loginResponseDto.getUser());
    }

}
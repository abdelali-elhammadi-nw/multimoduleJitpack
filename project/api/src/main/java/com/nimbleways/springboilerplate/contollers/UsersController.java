package com.nimbleways.springboilerplate.contollers;

import com.nimbleways.springboilerplate.dto.UpdateUserRequestDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
public class UsersController {
    private final IUserService userService;

    public UsersController(final IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable String id, @RequestBody UpdateUserRequestDTO user){
        return userService.updateUser(UUID.fromString(id), user);
    }

}

package com.nimbleways.springboilerplate.dto;

import com.nimbleways.springboilerplate.utils.NimblewaysEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank @NimblewaysEmail
    private String email;

    @NotBlank
    private String password;

}

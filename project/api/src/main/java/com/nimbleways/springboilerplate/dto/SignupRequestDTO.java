package com.nimbleways.springboilerplate.dto;

import com.nimbleways.springboilerplate.utils.NimblewaysEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDTO {

    @NotBlank
    private String name;

    @NotBlank @NimblewaysEmail
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;

    @NotNull
    private LocalDate employmentDate;

}
package com.nimbleways.springboilerplate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseRequestDTO {

    @NotBlank
    private String model;

    @NotBlank
    private String brand;

    @NotBlank
    private String store;

    @Min(0)
    private Double price;

}

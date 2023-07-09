package com.nimbleways.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {

    private String id;
    private String brand;
    private String model;
    private Double price;
    private String store;
    private int rating;
    private String userId;
    private List<String> media;

}

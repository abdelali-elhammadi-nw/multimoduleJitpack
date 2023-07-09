package com.nimbleways.springboilerplate.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRatingDTO {

    @Min(1) @Max(5)
    private int rating;

}

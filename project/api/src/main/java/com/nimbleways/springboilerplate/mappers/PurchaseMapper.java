package com.nimbleways.springboilerplate.mappers;

import com.nimbleways.springboilerplate.dto.CreatePurchaseRequestDTO;
import com.nimbleways.springboilerplate.dto.PurchaseDTO;
import com.nimbleways.springboilerplate.entities.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper( PurchaseMapper.class );

    @Mapping(target="id", expression = "java(purchase.getId().toString())")
    @Mapping(target="userId", expression = "java(purchase.getUser().getId().toString())")
    PurchaseDTO purchaseToPurchaseDTO(Purchase purchase);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "rating",ignore = true)
    @Mapping(target = "media", ignore = true)
    PurchaseDTO createPurchaseRequestDTOToPurchaseDTO(CreatePurchaseRequestDTO purchaseRequestDTO);

    default Purchase purchaseDTOToPurchase(final PurchaseDTO purchaseDTO){
        if(purchaseDTO==null) {
            return null;
        }
        final Purchase purchase = new Purchase();
        purchase.setBrand(purchaseDTO.getBrand());
        purchase.setModel(purchaseDTO.getModel());
        purchase.setStore(purchaseDTO.getStore());
        purchase.setPrice(purchaseDTO.getPrice());
        purchase.setRating(purchaseDTO.getRating());
        if(purchaseDTO.getId() != null){
            purchase.setId(UUID.fromString(purchaseDTO.getId()));
        }

        return purchase;
    }
}

package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.dto.CreatePurchaseRequestDTO;
import com.nimbleways.springboilerplate.dto.PurchaseDTO;
import com.nimbleways.springboilerplate.dto.PurchaseRatingDTO;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPurchaseService {

    List<PurchaseDTO> getUserPurchases(UUID userId);

    PurchaseDTO getDetails(UUID id);

    PurchaseDTO createPurchase(CreatePurchaseRequestDTO purchaseRequestDTO, List<MultipartFile> images, UUID userId);

    PurchaseDTO ratePurchase(UUID id ,PurchaseRatingDTO purchaseRatingDTO);
}

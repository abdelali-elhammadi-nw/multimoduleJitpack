package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.dto.CreatePurchaseRequestDTO;
import com.nimbleways.springboilerplate.dto.PurchaseDTO;
import com.nimbleways.springboilerplate.dto.PurchaseRatingDTO;
import com.nimbleways.springboilerplate.entities.Purchase;
import com.nimbleways.springboilerplate.entities.User;
import com.nimbleways.springboilerplate.exceptions.PurchaseNotFoundException;
import com.nimbleways.springboilerplate.exceptions.UserNotFoundException;
import com.nimbleways.springboilerplate.mappers.PurchaseMapper;
import com.nimbleways.springboilerplate.repositories.PurchaseRepository;
import com.nimbleways.springboilerplate.repositories.UserRepository;
import com.nimbleways.springboilerplate.services.IFileStorageService;
import com.nimbleways.springboilerplate.services.IPurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final IFileStorageService fileStorageService;

    public PurchaseService(final PurchaseRepository purchaseRepository,
                           final UserRepository userRepository,
                           final IFileStorageService fileStorageService){
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<PurchaseDTO> getUserPurchases(UUID userId) {

        List<Purchase> purchases = purchaseRepository.findAllByUser_Id(userId);
        PurchaseMapper mapper = PurchaseMapper.INSTANCE;
        return purchases.stream().map(mapper::purchaseToPurchaseDTO).toList();

    }

    @Override
    public PurchaseDTO getDetails(UUID id) {

        Purchase purchase = purchaseRepository.findById(id)
                                              .orElseThrow(()->new PurchaseNotFoundException(id.toString()));

        return PurchaseMapper.INSTANCE.purchaseToPurchaseDTO(purchase);

    }

    public PurchaseDTO createPurchase(CreatePurchaseRequestDTO purchaseRequestDTO,
                                      List<MultipartFile> images, UUID userId) {

        PurchaseDTO purchaseDTO = PurchaseMapper.INSTANCE.createPurchaseRequestDTOToPurchaseDTO(purchaseRequestDTO);
        Purchase purchase = PurchaseMapper.INSTANCE.purchaseDTOToPurchase(purchaseDTO);
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId.toString()));
        purchase.setUser(user);
        List<String> imagesNames = fileStorageService.saveAll(images);
        purchase.setMedia(imagesNames);
        purchase = purchaseRepository.save(purchase);
        return PurchaseMapper.INSTANCE.purchaseToPurchaseDTO(purchase);

    }

    @Override
    public PurchaseDTO ratePurchase(UUID id, PurchaseRatingDTO purchaseRatingDTO) {

        Purchase purchase = purchaseRepository.findById(id)
                            .orElseThrow(()->new PurchaseNotFoundException(id.toString()));

        purchase.setRating(purchaseRatingDTO.getRating());

        purchase = purchaseRepository.save(purchase);

        return PurchaseMapper.INSTANCE.purchaseToPurchaseDTO(purchase);

    }

}

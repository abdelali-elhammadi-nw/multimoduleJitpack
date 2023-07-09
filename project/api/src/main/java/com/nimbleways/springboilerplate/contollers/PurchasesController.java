package com.nimbleways.springboilerplate.contollers;

import com.nimbleways.springboilerplate.dto.CreatePurchaseRequestDTO;
import com.nimbleways.springboilerplate.dto.PurchaseDTO;
import com.nimbleways.springboilerplate.dto.PurchaseRatingDTO;
import com.nimbleways.springboilerplate.security.UserDetailsImpl;
import com.nimbleways.springboilerplate.services.IFileStorageService;
import com.nimbleways.springboilerplate.services.IPurchaseService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchases")
//@PreAuthorize("isAuthenticated()")
public class PurchasesController {

    private final IPurchaseService purchaseService;

    private final IFileStorageService fileStorageService;

    public PurchasesController(final IPurchaseService purchaseService,
                               final IFileStorageService fileStorageService){
        this.purchaseService = purchaseService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("")
    public List<PurchaseDTO> getPurchases(@RequestParam(defaultValue = "") String userId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        UUID id = userId.isEmpty()? userDetails.getId(): UUID.fromString(userId);
        return purchaseService.getUserPurchases( id );
    }

    @GetMapping("/{id}")
    public PurchaseDTO getPurchase(@PathVariable String id){

        return purchaseService.getDetails(UUID.fromString(id));

    }

    @PutMapping("/{id}")
    public PurchaseDTO ratePurchase(@PathVariable String id, @RequestBody @Valid PurchaseRatingDTO purchaseRatingDTO){

        return purchaseService.ratePurchase( UUID.fromString(id), purchaseRatingDTO );

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseDTO createPurchase(@RequestParam(name="files") List<MultipartFile> images ,
                                      @ModelAttribute @Valid CreatePurchaseRequestDTO purchaseRequestDTO,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return purchaseService.createPurchase(purchaseRequestDTO , images , userDetails.getId());
    }

    @GetMapping("/{id}/{media}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Resource> getPurchaseMedia(@PathVariable(name="id") String id,
                                                     @PathVariable(name="media")String media) {
        Resource file = fileStorageService.load(media);
        return ResponseEntity.status(HttpStatus.OK).body(file);
    }

}

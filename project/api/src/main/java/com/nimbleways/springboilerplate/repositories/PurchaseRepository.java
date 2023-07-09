package com.nimbleways.springboilerplate.repositories;

import com.nimbleways.springboilerplate.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findAllByUser_Id(UUID userId);

}

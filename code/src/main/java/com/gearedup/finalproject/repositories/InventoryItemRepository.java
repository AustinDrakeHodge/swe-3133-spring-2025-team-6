package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findBySoldFalseOrderByPriceDesc();
    List<InventoryItem> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndSoldFalse(String name, String description);

}

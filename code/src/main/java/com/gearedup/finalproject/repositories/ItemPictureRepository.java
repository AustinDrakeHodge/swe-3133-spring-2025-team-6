package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.InventoryItem;
import com.gearedup.finalproject.entities.ItemPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPictureRepository extends JpaRepository<ItemPicture, Long> {
        List<ItemPicture> findByInventoryItem(InventoryItem inventoryItem);
}


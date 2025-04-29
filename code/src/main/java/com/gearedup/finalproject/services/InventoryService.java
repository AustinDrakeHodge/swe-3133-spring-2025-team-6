package com.gearedup.finalproject.services;

import com.gearedup.finalproject.entities.*;
import com.gearedup.finalproject.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryItemRepository inventoryItemRepository;
    private final ItemPictureRepository itemPictureRepository;
    private final ItemTypeRepository itemTypeRepository;

    public List<InventoryItem> getAvailableItems() {
        return inventoryItemRepository.findBySoldFalseOrderByPriceDesc();
    }

    public List<InventoryItem> searchItems(String keyword) {
        return inventoryItemRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndSoldFalse(keyword, keyword);
    }

  public InventoryItem addInventoryItem(String name, String description, BigDecimal price, Long itemTypeId, List<String> imageUrls) {
    ItemType type = itemTypeRepository.findById(itemTypeId)
        .orElseThrow(() -> new RuntimeException("ItemType not found"));

    InventoryItem item = InventoryItem.builder()
            .name(name)
            .description(description)
            .price(price)
            .sold(false)
            .itemType(type)
            .build();
    item = inventoryItemRepository.save(item);

    for (String url : imageUrls) {
        ItemPicture picture = ItemPicture.builder()
                .imageUrl(url)
                .inventoryItem(item)
                .build();
        itemPictureRepository.save(picture);
    }

    return item;
}

    public InventoryItem getInventoryItemByItemId(Long itemId) {
        return inventoryItemRepository.findById(itemId)
        .orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
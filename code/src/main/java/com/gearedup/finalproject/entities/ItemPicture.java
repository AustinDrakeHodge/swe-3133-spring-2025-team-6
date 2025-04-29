package com.gearedup.finalproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_pictures")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ItemPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl; // Save path or URL

    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;
}
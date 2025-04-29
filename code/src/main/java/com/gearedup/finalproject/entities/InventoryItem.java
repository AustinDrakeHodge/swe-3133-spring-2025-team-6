package com.gearedup.finalproject.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // maps to ItemID

    private String name;
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private boolean sold;

     @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL)
    private List<ItemPicture> pictures = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "item_type_id")
    private ItemType itemType; // NEW field
}
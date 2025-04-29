package com.gearedup.finalproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // maps to ItemTypeID

    @Column(nullable = false, unique = true)
    private String typeName;
}
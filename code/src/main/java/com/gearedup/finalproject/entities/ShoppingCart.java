package com.gearedup.finalproject.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_carts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
}
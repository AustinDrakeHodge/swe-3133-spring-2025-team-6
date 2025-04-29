package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.CartItem;
import com.gearedup.finalproject.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(ShoppingCart cart);
}
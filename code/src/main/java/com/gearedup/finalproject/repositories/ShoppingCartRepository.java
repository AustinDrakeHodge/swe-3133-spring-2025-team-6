package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.ShoppingCart;
import com.gearedup.finalproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
}
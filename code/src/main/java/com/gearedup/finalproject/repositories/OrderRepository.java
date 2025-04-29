package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
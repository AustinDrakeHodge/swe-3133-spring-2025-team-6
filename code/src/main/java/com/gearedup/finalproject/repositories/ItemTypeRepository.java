package com.gearedup.finalproject.repositories;

import com.gearedup.finalproject.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    Optional<ItemType> findByTypeName(String typeName);
}
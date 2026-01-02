package com.example.horsestablesystem.repository;

import com.example.horsestablesystem.entity.HorseEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HorseRepo extends JpaRepository<HorseEntity, Long> {
    List<HorseEntity> findByHorsePriceBetweenOrderByHorsePriceAsc(Double minPrice, Double maxPrice);
    long countByStableStableId(Long stableId);
    boolean existsByHorseName(String horseName);
}

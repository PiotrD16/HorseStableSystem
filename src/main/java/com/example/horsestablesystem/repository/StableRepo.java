package com.example.horsestablesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.horsestablesystem.entity.StableEntity;

public interface StableRepo extends JpaRepository<StableEntity, Long> {
    boolean existsByStableName(String stableName);
    StableEntity findByStableName(String stableName);
}

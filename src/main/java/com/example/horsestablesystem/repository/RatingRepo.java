package com.example.horsestablesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.horsestablesystem.entity.RatingEntity;

public interface RatingRepo extends JpaRepository<RatingEntity, Long>{
    @Query("SELECT AVG(r.value) FROM RatingEntity r WHERE r.horse.horseId = ?1")
    Double findHorseAverageRating(Long horseId);
}
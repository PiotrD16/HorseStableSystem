package com.example.horsestablesystem.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.horsestablesystem.entity.RatingEntity;
import com.example.horsestablesystem.repository.RatingRepo;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepository;

    // Zwracam średnią ocenę dla konia
    public double getAverageRatingForHorse(Long horseId) {
        Double average = ratingRepository.findHorseAverageRating(horseId);
        return average != null ? average : 0.0;
    }

    // Dodaje rating dla konia
    public RatingEntity addRating(RatingEntity rating) {
        return ratingRepository.save(rating);
    }
}

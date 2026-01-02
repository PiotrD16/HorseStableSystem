package com.example.horsestablesystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.horsestablesystem.entity.RatingEntity;
import com.example.horsestablesystem.service.RatingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RatingController {

    private final RatingService ratingService;

    // Endpoint nr. 3
    @GetMapping("/horse/rating/{horseId}")
    public ResponseEntity<Double> getAverageRatingForHorse(@PathVariable Long horseId) {
        double averageRating = ratingService.getAverageRatingForHorse(horseId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    // Endpoint nr. 4
    @PostMapping("/horse/rating")
    public ResponseEntity<RatingEntity> addRating(@RequestBody RatingEntity rating) {
        RatingEntity createdRating = ratingService.addRating(rating);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }
}

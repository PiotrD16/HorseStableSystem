package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.exception.HorseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.horsestablesystem.dto.rating.RatingCreateDTO;
import com.example.horsestablesystem.dto.rating.RatingResponseDTO;
import com.example.horsestablesystem.service.RatingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horse")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rating")
    public ResponseEntity<RatingResponseDTO> addRating(@Valid @RequestBody RatingCreateDTO dto) throws HorseNotFoundException {
        RatingResponseDTO response = ratingService.addRating(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

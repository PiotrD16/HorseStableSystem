package com.example.horsestablesystem.service;

import org.springframework.stereotype.Service;

import com.example.horsestablesystem.dto.rating.RatingCreateDTO;
import com.example.horsestablesystem.dto.rating.RatingResponseDTO;
import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.entity.RatingEntity;
import com.example.horsestablesystem.exception.HorseNotFoundException;
import com.example.horsestablesystem.repository.HorseRepo;
import com.example.horsestablesystem.repository.RatingRepo;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepo ratingRepository;
    private final HorseRepo horseRepo;

    // Średnia ocen konia
    public double getAverageRatingForHorse(Long horseId) {
        Double avg = ratingRepository.findHorseAverageRating(horseId);
        return avg != null ? avg : 0.0;
    }

    // Dodanie ratingu
    public RatingResponseDTO addRating(RatingCreateDTO dto) throws HorseNotFoundException {

        HorseEntity horse = horseRepo.findById(dto.getHorseId())
                .orElseThrow(() -> new HorseNotFoundException("Nie znaleziono konia"));

        RatingEntity rating = new RatingEntity();
        rating.setHorse(horse);
        rating.setValue(dto.getValue());
        rating.setDescription(dto.getDescription());
        rating.setDateRated(LocalDate.now());

        RatingEntity saved = ratingRepository.save(rating);

        RatingResponseDTO response = new RatingResponseDTO();
        response.setId(saved.getId());
        response.setValue(saved.getValue());
        response.setDescription(saved.getDescription());
        response.setDateRated(saved.getDateRated());
        response.setHorseId(saved.getHorse().getHorseId());

        return response;
    }
}

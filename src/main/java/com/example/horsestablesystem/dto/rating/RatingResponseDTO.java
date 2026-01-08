package com.example.horsestablesystem.dto.rating;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RatingResponseDTO {

    private Long id;
    private int value;
    private String description;
    private LocalDate dateRated;
    private Long horseId;
}

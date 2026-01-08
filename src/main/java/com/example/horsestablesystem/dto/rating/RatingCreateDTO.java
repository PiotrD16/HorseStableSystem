package com.example.horsestablesystem.dto.rating;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingCreateDTO {

    @Min(1)
    @Max(5)
    private int value;

    private String description;

    @NotNull
    private Long horseId;
}

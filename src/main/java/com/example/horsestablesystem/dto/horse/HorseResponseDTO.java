package com.example.horsestablesystem.dto.horse;

import java.util.List;
import com.example.horsestablesystem.dto.rating.RatingResponseDTO;
import lombok.Data;

@Data
public class HorseResponseDTO {

    private Long horseId;
    private String horseName;
    private Integer horseAge;
    private String horseBreed;
    private String horseDescription;
    private String horseStatus;
    private String horseType;
    private Double horsePrice;
    private Double horseWeight;

    private String stableName;
    private List<RatingResponseDTO> ratings;
    private Double averageRating;
}

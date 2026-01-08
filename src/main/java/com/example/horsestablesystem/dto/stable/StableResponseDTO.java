package com.example.horsestablesystem.dto.stable;

import java.util.List;
import com.example.horsestablesystem.dto.horse.HorseListDTO;
import lombok.Data;

@Data
public class StableResponseDTO {

    private Long stableId;
    private String stableName;
    private String stableDescription;
    private String location;
    private int maxCapacity;
    private int establishedYear;

    private List<HorseListDTO> horses;
}

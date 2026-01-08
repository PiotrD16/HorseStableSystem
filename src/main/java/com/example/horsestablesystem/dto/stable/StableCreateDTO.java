package com.example.horsestablesystem.dto.stable;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StableCreateDTO {

    @NotBlank
    private String stableName;

    private String stableDescription;

    @NotBlank
    private String location;

    @Min(1)
    private int maxCapacity;

    @Min(1800)
    private int establishedYear;
}

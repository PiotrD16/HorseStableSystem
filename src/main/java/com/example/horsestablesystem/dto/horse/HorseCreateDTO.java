package com.example.horsestablesystem.dto.horse;

import com.example.horsestablesystem.enums.HorseCondition;
import com.example.horsestablesystem.enums.HorseType;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class HorseCreateDTO {

    @NotBlank
    private String horseName;

    @NotNull
    @Min(1)
    private Integer horseAge;

    private String horseBreed;
    private String horseDescription;

    @NotNull
    private HorseCondition horseStatus;

    @NotNull
    private HorseType horseType;

    @Positive
    private Double horsePrice;

    @Positive
    private Double horseWeight;

    @NotNull
    private Long stableId;
}


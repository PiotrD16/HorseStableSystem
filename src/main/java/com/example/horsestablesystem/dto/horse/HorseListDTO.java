package com.example.horsestablesystem.dto.horse;

import lombok.Data;

@Data
public class HorseListDTO {

    private Long horseId;
    private String horseName;
    private String horseBreed;
    private Integer horseAge;
}

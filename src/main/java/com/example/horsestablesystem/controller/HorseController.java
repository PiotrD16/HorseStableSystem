package com.example.horsestablesystem.controller;

import java.util.List;

import com.example.horsestablesystem.exception.HorseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.horsestablesystem.dto.horse.HorseCreateDTO;
import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.horse.HorseResponseDTO;
import com.example.horsestablesystem.service.HorseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horses")
@RequiredArgsConstructor
public class HorseController {

    private final HorseService horseService;

    @GetMapping
    public ResponseEntity<List<HorseListDTO>> getAllHorses() {
        List<HorseListDTO> horses = horseService.getAllHorses();
        return new ResponseEntity<>(horses, HttpStatus.OK);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<HorseListDTO>> getHorsesInPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<HorseListDTO> horses = horseService.getHorsesinPriceRange(min, max);
        return new ResponseEntity<>(horses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HorseResponseDTO> addHorse(@Valid @RequestBody HorseCreateDTO dto) {
        HorseResponseDTO savedHorse = horseService.addHorse(dto);
        return new ResponseEntity<>(savedHorse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorse(@PathVariable Long id) throws HorseNotFoundException {
        horseService.deleteHorse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

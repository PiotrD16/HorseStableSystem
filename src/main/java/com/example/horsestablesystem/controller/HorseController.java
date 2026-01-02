package com.example.horsestablesystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.exception.HorseNotFoundException;
import com.example.horsestablesystem.service.HorseService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class HorseController {
    private final HorseService horseService;

    // Endpoint nr. 1
    @PostMapping("/horse")
    public ResponseEntity<HorseEntity> addHorse(@RequestBody HorseEntity horse) {
        HorseEntity createdHorse = horseService.addHorse(horse);
        return new ResponseEntity<>(createdHorse, HttpStatus.CREATED);
    }
    
    // Endpoint nr. 2
    @DeleteMapping("/horse/{horseId}")
    public ResponseEntity<Void> deleteHorse(@PathVariable Long horseId) throws HorseNotFoundException{
        horseService.deleteHorse(horseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Szukanie koni po widełkach cenowych
    @GetMapping("/horse/range")
    public ResponseEntity<List<HorseEntity>> getHorsesInPriceRange (
        @RequestParam Double minPrice,
        @RequestParam Double maxPrice
    ) {
        List<HorseEntity> horses = horseService.getHorsesinPriceRange(minPrice, maxPrice);
        return new ResponseEntity <>(horses, HttpStatus.OK);
    }
}

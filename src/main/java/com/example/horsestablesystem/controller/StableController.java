package com.example.horsestablesystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.stable.StableCreateDTO;
import com.example.horsestablesystem.dto.stable.StableResponseDTO;
import com.example.horsestablesystem.service.StableService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stables")
@RequiredArgsConstructor
public class StableController {

    private final StableService stableService;

    @GetMapping
    public ResponseEntity<List<StableResponseDTO>> getAllStables() {
        List<StableResponseDTO> stables = stableService.getAllStables();
        return new ResponseEntity<>(stables, HttpStatus.OK);
    }

    @GetMapping("/{id}/horses")
    public ResponseEntity<List<HorseListDTO>> getHorsesInStable(@PathVariable Long id) {
        List<HorseListDTO> horses = stableService.getHorsesInStable(id);
        return new ResponseEntity<>(horses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StableResponseDTO> addStable(@Valid @RequestBody StableCreateDTO dto) {
        StableResponseDTO savedStable = stableService.addStable(dto);
        return new ResponseEntity<>(savedStable, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStable(@PathVariable Long id) {
        stableService.deleteStable(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/occupancy")
    public ResponseEntity<Double> getStableOccupancy(@PathVariable Long id) {
        double occupancy = stableService.getStableOccupancyRate(id);
        return new ResponseEntity<>(occupancy, HttpStatus.OK);
    }
}

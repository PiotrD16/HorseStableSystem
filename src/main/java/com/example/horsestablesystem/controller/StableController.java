package com.example.horsestablesystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.entity.StableEntity;
import com.example.horsestablesystem.service.StableService;

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

public class StableController {
    private final StableService stableService;
    
    // Endpoint nr. 5
    @GetMapping("stable")
    public ResponseEntity<List<StableEntity>> getAllStables() {
        List<StableEntity> stables = stableService.getAllStables();
        return ResponseEntity.ok(stables);
    }
    
    // Endpoint nr. 6
    @GetMapping("stable/{stableId}")
    public ResponseEntity<List<HorseEntity>> getHorsesByStable (@PathVariable Long stableId) {
        List<HorseEntity> horses = stableService.getHorsesInStable(stableId);
        return ResponseEntity.ok(horses);
    }

    // Endpoint nr. 7
    @GetMapping("stable/{stableId}/csv")
    public ResponseEntity<Void> exportHorsesToCSV (@PathVariable Long stableId){
        String filePath = "stable_" + stableId + "_horses.csv";
        stableService.exportHorsesInStableToCSV(stableId, filePath);
        return ResponseEntity.ok().build();
    }

    // Endpoint nr. 8
    @PostMapping("/stable")
    public ResponseEntity<StableEntity> addStable(@RequestBody StableEntity stable){
        StableEntity createdStable = stableService.addStable(stable);
        return new ResponseEntity <>(createdStable, HttpStatus.CREATED);
    }

    // Endpoint nr. 9
    @DeleteMapping("/stable/{stableId}")
    public ResponseEntity<Void> deleteStable (@PathVariable Long stableId){
        stableService.deleteStable(stableId);
        return new ResponseEntity <> (HttpStatus.NO_CONTENT);
    }

    // Endpoint nr. 10
    @GetMapping("/stable/{stableId}/fill")
    public ResponseEntity<Double> getStableCapacity (@PathVariable Long stableId){
        Double capacity = stableService.getStableOccupancyRate(stableId);
        return new ResponseEntity <> (capacity, HttpStatus.OK);
    }
}

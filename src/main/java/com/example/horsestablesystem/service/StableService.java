package com.example.horsestablesystem.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.entity.StableEntity;
import com.example.horsestablesystem.exception.StableNotFoundException;
import com.example.horsestablesystem.repository.HorseRepo;
import com.example.horsestablesystem.repository.StableRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StableService {
    private final StableRepo stableRepo;
    private final HorseRepo horseRepo;

    // Zwraca wszystkie stadniny
    public List<StableEntity> getAllStables() {
        return stableRepo.findAll();
    }

    // Zwraca wszystkie konie w stadninie
    public List<HorseEntity> getHorsesInStable(Long stableId) {
        Optional<StableEntity> stable = stableRepo.findById(stableId);
        if(stable.isPresent()) {
            return stable.get().getHorses();
        } else {
            throw new StableNotFoundException("Stable not found with ID: " + stableId);
        }
    }

    // To samo co wyżej plus eksport do CSV
    public void exportHorsesInStableToCSV(Long stableId, String filePath) {
        Optional<StableEntity> stable = stableRepo.findById(stableId);

        if(stable.isPresent()) {
            List<HorseEntity> horses = stable.get().getHorses();
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            
            writer.println("ID;Nazwa;Wiek;Rasa;Status;Typ;Cena;Waga");

            for (HorseEntity horse : horses) {
                writer.print(horse.getHorseId() + ";");
                writer.print(horse.getHorseName() + ";");
                writer.print(horse.getHorseAge() + ";");
                writer.print(horse.getHorseBreed() + ";");
                writer.print(horse.getHorseStatus() + ";");
                writer.print(horse.getHorseType() + ";");
                writer.print(horse.getHorsePrice() + ";");
                writer.println(horse.getHorseWeight());
            }

            } catch (IOException e) {
                throw new RuntimeException("Błąd podczas generowania pliku CSV: " + e.getMessage());
            }
        } 
        else {
            throw new StableNotFoundException("Stable not found with ID: " + stableId);
        }
    }

    // Dodaje nową stadninę
    public StableEntity addStable(StableEntity stable) {
        if(stable == null) throw new IllegalArgumentException("Stable cannot be null");

        if(stableRepo.existsByStableName(stable.getStableName())) {
            throw new IllegalArgumentException("Stable with name " + stable.getStableName() + " already exists.");
        }

        stable.setStableId(null);
        return stableRepo.save(stable);
    }

    // Usuwa stadninę
    @Transactional
    public void deleteStable(Long stableId) {
        if(!stableRepo.existsById(stableId)) {
            throw new StableNotFoundException("Stable not found with ID: " + stableId);
        }
        stableRepo.deleteById(stableId);
    }

    // Zwraca zapełnienie stadniny w procentach
    public double getStableOccupancyRate(Long stableId) {
        Optional<StableEntity> stable = stableRepo.findById(stableId);
        if(stable.isPresent()) {
            StableEntity stableEntity = stable.get();
            long currentOccupancy = horseRepo.countByStableStableId(stableId);
            int maxCapacity = stableEntity.getMaxCapacity();
            return (currentOccupancy / (double) maxCapacity) * 100.0;
        } else {
            throw new StableNotFoundException("Stable not found with ID: " + stableId);
        }
    }
}


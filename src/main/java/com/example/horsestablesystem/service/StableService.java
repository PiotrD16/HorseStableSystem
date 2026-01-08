package com.example.horsestablesystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.stable.StableCreateDTO;
import com.example.horsestablesystem.dto.stable.StableResponseDTO;
import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.entity.StableEntity;
import com.example.horsestablesystem.exception.StableNotFoundException;
import com.example.horsestablesystem.repository.HorseRepo;
import com.example.horsestablesystem.repository.StableRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StableService {

    private final StableRepo stableRepo;
    private final HorseRepo horseRepo;

    // Wszystkie stadniny
    public List<StableResponseDTO> getAllStables() {
        return stableRepo.findAll().stream()
                .map(this::toStableResponseDTO)
                .toList();
    }

    // Konie w stadninie
    public List<HorseListDTO> getHorsesInStable(Long stableId) {

        StableEntity stable = stableRepo.findById(stableId)
                .orElseThrow(() -> new StableNotFoundException("Stable not found with ID: " + stableId));

        return stable.getHorses().stream()
                .map(this::toHorseListDTO)
                .toList();
    }

    // Dodanie stadniny
    public StableResponseDTO addStable(StableCreateDTO dto) {

        if (stableRepo.existsByStableName(dto.getStableName())) {
            throw new IllegalArgumentException("Stable with name " + dto.getStableName() + " already exists.");
        }

        StableEntity stable = new StableEntity();
        stable.setStableId(null);
        stable.setStableName(dto.getStableName());
        stable.setStableDescription(dto.getStableDescription());
        stable.setLocation(dto.getLocation());
        stable.setMaxCapacity(dto.getMaxCapacity());
        stable.setEstablishedYear(dto.getEstablishedYear());

        return toStableResponseDTO(stableRepo.save(stable));
    }

    // Usunięcie stadniny
    public void deleteStable(Long stableId) {
        if (!stableRepo.existsById(stableId)) {
            throw new StableNotFoundException("Stable not found with ID: " + stableId);
        }
        stableRepo.deleteById(stableId);
    }

    // Zapełnienie stadniny
    public double getStableOccupancyRate(Long stableId) {
        StableEntity stable = stableRepo.findById(stableId)
                .orElseThrow(() -> new StableNotFoundException("Stable not found with ID: " + stableId));

        long current = horseRepo.countByStableStableId(stableId);
        return (current / (double) stable.getMaxCapacity()) * 100.0;
    }

    // ========= MAPPER =========
    private StableResponseDTO toStableResponseDTO(StableEntity stable) {
        StableResponseDTO dto = new StableResponseDTO();
        dto.setStableId(stable.getStableId());
        dto.setStableName(stable.getStableName());
        dto.setStableDescription(stable.getStableDescription());
        dto.setLocation(stable.getLocation());
        dto.setMaxCapacity(stable.getMaxCapacity());
        dto.setEstablishedYear(stable.getEstablishedYear());
        dto.setHorses(stable.getHorses().stream().map(this::toHorseListDTO).toList());
        return dto;
    }

    private HorseListDTO toHorseListDTO(HorseEntity horse) {
        HorseListDTO dto = new HorseListDTO();
        dto.setHorseId(horse.getHorseId());
        dto.setHorseName(horse.getHorseName());
        dto.setHorseAge(horse.getHorseAge());
        dto.setHorseBreed(horse.getHorseBreed());
        return dto;
    }
}

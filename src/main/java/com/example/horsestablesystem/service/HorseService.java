package com.example.horsestablesystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.entity.StableEntity;
import com.example.horsestablesystem.exception.HorseExistsException;
import com.example.horsestablesystem.exception.HorseNotFoundException;
import com.example.horsestablesystem.exception.StableNotFoundException;
import com.example.horsestablesystem.repository.HorseRepo;
import com.example.horsestablesystem.repository.StableRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorseService {
    private final HorseRepo horseRepo;
    private final StableRepo stableRepo;

    // Wszystkie konie
    public List<HorseEntity> getAllHorses() {
        return horseRepo.findAll();
    }

    // Wyszukiwanie konia w widełkach cenowych
    public List<HorseEntity> getHorsesinPriceRange(Double minPrice, Double maxPrice) {
        return horseRepo.findByHorsePriceBetweenOrderByHorsePriceAsc(minPrice, maxPrice);
    }

    // Dodanie konia
    public HorseEntity addHorse(HorseEntity horse) {
        if (horse.getStable() == null || horse.getStable().getStableName() == null) {
        throw new IllegalArgumentException("Musisz podać dane stajni!");
        }

        StableEntity stable = stableRepo.findByStableName(horse.getStable().getStableName());

        if (!stableRepo.existsByStableName(horse.getStable().getStableName())) {
            throw new StableNotFoundException("Podana stadnina nie istnieje: " + horse.getStable().getStableName());
        }

        if (horseRepo.existsByHorseName(horse.getHorseName())) {
            throw new HorseExistsException("Koń o imieniu " + horse.getHorseName() + " już jest w systemie!");
        }
        
        horse.setHorseId(null);
        horse.setStable(stable);
        return horseRepo.save(horse);
    }

    // Usunięcie konia
    public void deleteHorse(Long horseId) throws HorseNotFoundException {
        Optional<HorseEntity> horse = horseRepo.findById(horseId);
        if (horse.isPresent()) {
            horseRepo.deleteById(horseId);
        } else {
            throw new HorseNotFoundException("Nie znaleziono konia!");
        }
    }
}

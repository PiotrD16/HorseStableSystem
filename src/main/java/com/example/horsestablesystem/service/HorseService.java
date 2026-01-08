package com.example.horsestablesystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.horsestablesystem.dto.horse.HorseCreateDTO;
import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.horse.HorseResponseDTO;
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
    public List<HorseListDTO> getAllHorses() {
        return horseRepo.findAll().stream()
                .map(this::toHorseListDTO)
                .toList();
    }

    // Wyszukiwanie koni w przedziale cenowym
    public List<HorseListDTO> getHorsesinPriceRange(Double minPrice, Double maxPrice) {
        return horseRepo.findByHorsePriceBetweenOrderByHorsePriceAsc(minPrice, maxPrice).stream()
                .map(this::toHorseListDTO)
                .toList();
    }

    // Dodanie konia
    public HorseResponseDTO addHorse(HorseCreateDTO dto) {

        if (!stableRepo.existsById(dto.getStableId())) {
            throw new StableNotFoundException("Podana stadnina nie istnieje");
        }

        if (horseRepo.existsByHorseName(dto.getHorseName())) {
            throw new HorseExistsException("Koń o imieniu " + dto.getHorseName() + " już jest w systemie!");
        }

        StableEntity stable = stableRepo.findById(dto.getStableId()).get();

        HorseEntity horse = new HorseEntity();
        horse.setHorseId(null);
        horse.setHorseName(dto.getHorseName());
        horse.setHorseAge(dto.getHorseAge());
        horse.setHorseBreed(dto.getHorseBreed());
        horse.setHorseDescription(dto.getHorseDescription());
        horse.setHorseStatus(dto.getHorseStatus());
        horse.setHorseType(dto.getHorseType());
        horse.setHorsePrice(dto.getHorsePrice());
        horse.setHorseWeight(dto.getHorseWeight());
        horse.setStable(stable);

        HorseEntity saved = horseRepo.save(horse);
        return toHorseResponseDTO(saved);
    }

    // Usunięcie konia
    public void deleteHorse(Long horseId) throws HorseNotFoundException {
        if (!horseRepo.existsById(horseId)) {
            throw new HorseNotFoundException("Nie znaleziono konia!");
        }
        horseRepo.deleteById(horseId);
    }

    // ========= MAPPER =========
    private HorseListDTO toHorseListDTO(HorseEntity horse) {
        HorseListDTO dto = new HorseListDTO();
        dto.setHorseId(horse.getHorseId());
        dto.setHorseName(horse.getHorseName());
        dto.setHorseAge(horse.getHorseAge());
        dto.setHorseBreed(horse.getHorseBreed());
        return dto;
    }

    private HorseResponseDTO toHorseResponseDTO(HorseEntity horse) {
        HorseResponseDTO dto = new HorseResponseDTO();
        dto.setHorseId(horse.getHorseId());
        dto.setHorseName(horse.getHorseName());
        dto.setHorseAge(horse.getHorseAge());
        dto.setHorseBreed(horse.getHorseBreed());
        dto.setHorseDescription(horse.getHorseDescription());
        dto.setHorseStatus(horse.getHorseStatus().name());
        dto.setHorseType(horse.getHorseType().name());
        dto.setHorsePrice(horse.getHorsePrice());
        dto.setHorseWeight(horse.getHorseWeight());
        dto.setStableName(horse.getStable().getStableName());
        return dto;
    }
}

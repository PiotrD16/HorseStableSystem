package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.entity.HorseEntity;
import com.example.horsestablesystem.service.HorseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HorseController.class)
public class HorseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HorseService horseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test // Endpoint 1: Dodawanie konia
    void shouldAddHorse() throws Exception {
        HorseEntity horse = new HorseEntity();
        horse.setHorseName("Iskra");
        
        when(horseService.addHorse(any(HorseEntity.class))).thenReturn(horse);

        mockMvc.perform(post("/api/horse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(horse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.horseName").value("Iskra"));
    }

    @Test // Endpoint 2: Usuwanie konia
    void shouldDeleteHorse() throws Exception {
        mockMvc.perform(delete("/api/horse/1"))
                .andExpect(status().isNoContent());
    }

    @Test // Szukanie w widełkach cenowych
    void shouldGetHorsesInPriceRange() throws Exception {
        when(horseService.getHorsesinPriceRange(1000.0, 5000.0))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/horse/range")
                .param("minPrice", "1000.0")
                .param("maxPrice", "5000.0"))
                .andExpect(status().isOk());
    }
}
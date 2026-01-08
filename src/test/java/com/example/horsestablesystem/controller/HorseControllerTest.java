package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.dto.horse.HorseCreateDTO;
import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.horse.HorseResponseDTO;
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

    @Test
    void shouldAddHorse() throws Exception {
        HorseResponseDTO horse = new HorseResponseDTO();
        horse.setHorseName("Iskra");

        when(horseService.addHorse(any(HorseCreateDTO.class))).thenReturn(horse);

        HorseCreateDTO createDTO = new HorseCreateDTO();
        createDTO.setHorseName("Iskra");

        mockMvc.perform(post("/api/horses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.horseName").value("Iskra"));
    }

    @Test
    void shouldDeleteHorse() throws Exception {
        mockMvc.perform(delete("/api/horses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetHorsesInPriceRange() throws Exception {
        when(horseService.getHorsesinPriceRange(1000.0, 5000.0))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/horses/price-range")
                        .param("min", "1000.0")
                        .param("max", "5000.0"))
                .andExpect(status().isOk());
    }
}

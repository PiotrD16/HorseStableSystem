package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.dto.horse.HorseListDTO;
import com.example.horsestablesystem.dto.stable.StableCreateDTO;
import com.example.horsestablesystem.dto.stable.StableResponseDTO;
import com.example.horsestablesystem.service.StableService;
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

@WebMvcTest(StableController.class)
public class StableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StableService stableService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllStables() throws Exception {
        when(stableService.getAllStables()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stables"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetHorsesByStableId() throws Exception {
        when(stableService.getHorsesInStable(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stables/1/horses"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddStable() throws Exception {
        StableResponseDTO response = new StableResponseDTO();
        response.setStableId(1L);
        response.setStableName("Zlota Podkowa");
        response.setStableDescription("Najlepsza stajnia w mieście");
        response.setLocation("Warszawa");
        response.setMaxCapacity(10);
        response.setEstablishedYear(2000);

        when(stableService.addStable(any(StableCreateDTO.class))).thenReturn(response);

        // Przygotowanie DTO do wysłania
        StableCreateDTO createDTO = new StableCreateDTO();
        createDTO.setStableName("Zlota Podkowa");
        createDTO.setStableDescription("Najlepsza stajnia w mieście");
        createDTO.setLocation("Warszawa");
        createDTO.setMaxCapacity(10);
        createDTO.setEstablishedYear(2000);

        mockMvc.perform(post("/api/stables")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stableName").value("Zlota Podkowa"))
                .andExpect(jsonPath("$.location").value("Warszawa"))
                .andExpect(jsonPath("$.maxCapacity").value(10));
    }


    @Test
    void shouldDeleteStable() throws Exception {
        mockMvc.perform(delete("/api/stables/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetStableOccupancy() throws Exception {
        when(stableService.getStableOccupancyRate(1L)).thenReturn(75.5);

        mockMvc.perform(get("/api/stables/1/occupancy"))
                .andExpect(status().isOk())
                .andExpect(content().string("75.5"));
    }
}

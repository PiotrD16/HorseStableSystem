package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.entity.StableEntity;
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

    @Test // Endpoint 5: Pobieranie wszystkich stajni
    void shouldGetAllStables() throws Exception {
        when(stableService.getAllStables()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stable"))
                .andExpect(status().isOk());
    }

    @Test // Endpoint 6: Konie w stajni
    void shouldGetHorsesByStableId() throws Exception {
        when(stableService.getHorsesInStable(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/stable/1"))
                .andExpect(status().isOk());
    }

    @Test // Endpoint 7: Eksport CSV
    void shouldExportCSV() throws Exception {
        mockMvc.perform(get("/api/stable/1/csv"))
                .andExpect(status().isOk());
    }

    @Test // Endpoint 8: Dodawanie stajni
    void shouldAddStable() throws Exception {
        StableEntity stable = new StableEntity();
        stable.setStableName("Zlota Podkowa");
        
        when(stableService.addStable(any(StableEntity.class))).thenReturn(stable);

        mockMvc.perform(post("/api/stable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stable)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.stableName").value("Zlota Podkowa"));
    }

    @Test // Endpoint 9: Usuwanie stajni
    void shouldDeleteStable() throws Exception {
        mockMvc.perform(delete("/api/stable/1"))
                .andExpect(status().isNoContent());
    }

    @Test // Endpoint 10: Zapełnienie stajni
    void shouldGetStableFill() throws Exception {
        when(stableService.getStableOccupancyRate(1L)).thenReturn(75.5);

        mockMvc.perform(get("/api/stable/1/fill"))
                .andExpect(status().isOk())
                .andExpect(content().string("75.5"));
    }
}
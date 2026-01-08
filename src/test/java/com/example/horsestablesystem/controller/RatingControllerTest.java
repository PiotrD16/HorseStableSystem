package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.dto.rating.RatingCreateDTO;
import com.example.horsestablesystem.dto.rating.RatingResponseDTO;
import com.example.horsestablesystem.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test // Endpoint: Dodawanie oceny
    void shouldAddRating() throws Exception {
        // Przygotowanie odpowiedzi z serwisu
        RatingResponseDTO response = new RatingResponseDTO();
        response.setId(1L);
        response.setValue(5);
        response.setDescription("Bardzo dobrze");
        response.setHorseId(1L);

        when(ratingService.addRating(any(RatingCreateDTO.class))).thenReturn(response);

        // Przygotowanie DTO do wysłania
        RatingCreateDTO createDTO = new RatingCreateDTO();
        createDTO.setHorseId(1L);
        createDTO.setValue(5);
        createDTO.setDescription("Bardzo dobrze");

        mockMvc.perform(post("/api/horse/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(5))
                .andExpect(jsonPath("$.horseId").value(1))
                .andExpect(jsonPath("$.description").value("Bardzo dobrze"));
    }

}

package com.example.horsestablesystem.controller;

import com.example.horsestablesystem.entity.RatingEntity;
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

    @Test // Endpoint 3: Średnia ocena
    void shouldGetAverageRating() throws Exception {
        when(ratingService.getAverageRatingForHorse(1L)).thenReturn(4.8);

        mockMvc.perform(get("/api/horse/rating/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.8"));
    }

    @Test // Endpoint 4: Dodawanie oceny
    void shouldAddRating() throws Exception {
        RatingEntity rating = new RatingEntity();
        rating.setValue(5);
        
        when(ratingService.addRating(any(RatingEntity.class))).thenReturn(rating);

        mockMvc.perform(post("/api/horse/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(5));
    }
}
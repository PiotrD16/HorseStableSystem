package com.example.horsestablesystem.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "date_rated", nullable = false)
    private LocalDate dateRated;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "value", nullable = false)
    private int value;

    @ManyToOne  
    @JoinColumn(name = "horse_id", nullable = false)
    private HorseEntity horse;

    // metoda pomocnicza do ustawiania daty na aktualny czas

    @PrePersist
    public void setCurrentDate() {
        this.dateRated = LocalDate.now();
    }
}

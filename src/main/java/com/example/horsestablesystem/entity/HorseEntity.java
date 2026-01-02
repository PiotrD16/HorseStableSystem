package com.example.horsestablesystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;

import com.example.horsestablesystem.enums.HorseCondition;
import com.example.horsestablesystem.enums.HorseType;

@Entity
@Table(name = "horse")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class HorseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "horse_id", nullable = false, unique = true)
    private Long horseId;
    
    @Column(name = "horse_age", nullable = false)
    private Integer horseAge;
    
    @Column(name = "horse_breed", length = 255)
    private String horseBreed;

    @Column(name = "horse_description", length = 255)
    private String horseDescription;

    @Column(name = "horse_status", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private HorseCondition horseStatus;
    @Column(name = "horse_name", length = 255, nullable = false)
    private String horseName;

    @Column(name = "horse_price")
    private Double horsePrice;
    @Column(name = "horse_type", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private HorseType horseType;

    @Column(name = "horse_weight")
    private Double horseWeight;

    @ManyToOne
    @JoinColumn(name = "id_stable")
    private StableEntity stable;
}

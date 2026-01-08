package com.example.horsestablesystem.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stable")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class StableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stable", nullable = false, unique = true)
    private Long stableId;

    @Column(name = "stable_description", length = 255)
    private String stableDescription;

    @Column(name = "established_year")
    private int establishedYear;

    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "stable_name", length = 255, nullable = false, unique = true)
    private String stableName;

    @OneToMany(mappedBy = "stable", cascade = CascadeType.ALL)
    @JsonManagedReference("stable-horse")
    private List<HorseEntity> horses = new ArrayList<>();
}

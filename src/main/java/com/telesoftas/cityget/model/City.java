package com.telesoftas.cityget.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "cities")
public class City {
    @Id
    private Long id;
    private String name;
    private String photo;
}


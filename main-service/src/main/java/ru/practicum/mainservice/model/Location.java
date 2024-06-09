package ru.practicum.mainservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * Класс места события LOCATION
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    /**
     * Идентификатор места события
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Широта
     */
    @Column(name = "lat", nullable = false)
    private Float lat;

    /**
     * Долгота
     */
    @Column(name = "lon", nullable = false)
    private Float lon;
}
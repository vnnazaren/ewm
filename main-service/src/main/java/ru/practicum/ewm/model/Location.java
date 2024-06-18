package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс места события LOCATION
 */
@Getter
@Setter
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
    @Column(name = "id")
    private Long id;

    /**
     * Широта
     */
    @Column(name = "lat")
    private Float lat;

    /**
     * Долгота
     */
    @Column(name = "lon")
    private Float lon;
}
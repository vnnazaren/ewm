package ru.practicum.mainservice.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс категории CATEGORY
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /**
     * Идентификатор категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Название категории
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
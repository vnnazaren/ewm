package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс "Подборка событий" - COMPILATION
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {

    /**
     * Идентификатор подборки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    /**
     * Заголовок подборки
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * Коллекция с событиями входящими в подборку
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Set<Event> events;
}
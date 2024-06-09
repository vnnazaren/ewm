package ru.practicum.mainservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


/**
 * Класс "Подборка событий" - COMPILATION
 */
@Data
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
    @Column(name = "id", nullable = false)
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private Set<Event> events;
}
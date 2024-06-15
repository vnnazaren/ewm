package ru.practicum.ewm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.ewm.util.EventStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс события EVENT
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    /**
     * Идентификатор события
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Краткое описание
     */
    @Column(name = "annotation")
    private String annotation;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    /**
     * Дата и время создания события
     */
    @Column(name = "created_on")
    @JsonFormat
    private LocalDateTime createdOn;

    /**
     * Полное описание события
     */
    @Column(name = "description")
    private String description;

    /**
     * Дата и время на которые намечено событие
     */
    @Column(name = "event_date")
    @JsonFormat
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие
     */
    @Column(name = "paid")
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Column(name = "participant_limit")
    private Integer participantLimit;

    /**
     * Дата и время публикации события
     */
    @Column(name = "published_on")
    @JsonFormat
    private LocalDateTime publishedOn;

    /**
     * Нужна ли пре-модерация заявок на участие
     */
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    /**
     * Список состояний жизненного цикла события
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EventStatus state;

    /**
     * Заголовок
     */
    @Column(name = "title")
    private String title;

    /**
     * Количество просмотров события
     */
    @Column(name = "views")
    private Integer views;

    /**
     * Категория
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    /**
     * Инициатор
     */
    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;

    /**
     * Широта и долгота места проведения события
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
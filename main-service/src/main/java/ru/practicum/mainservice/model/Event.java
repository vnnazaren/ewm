package ru.practicum.mainservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.mainservice.util.StateEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс события EVENT
 */
@Data
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
    @Column(name = "annotation", nullable = false)
    private String annotation;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    @Column(name = "confirmed_requests", nullable = false)
    private Integer confirmedRequests;

    /**
     * Дата и время создания события
     */
    @Column(name = "created_on", nullable = false)
    @JsonFormat
    private LocalDateTime createdOn;

    /**
     * Полное описание события
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Дата и время на которые намечено событие
     */
    @Column(name = "event_date", nullable = false)
    @JsonFormat
    private LocalDateTime eventDate;

    /**
     * Нужно ли оплачивать участие
     */
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Column(name = "participant_limit", nullable = false)
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
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    /**
     * Список состояний жизненного цикла события
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StateEnum state;

    /**
     * Заголовок
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Количество просмотров события
     */
    @Column(name = "views", nullable = false)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;

    /**
     * Широта и долгота места проведения события
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
package ru.practicum.mainservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.mainservice.util.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс заявки на участие в событии REQUEST
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    /**
     * Идентификатор заявки на участие в событии
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Дата и время создания заявки
     */
    @Column(name = "created", nullable = false)
    @JsonFormat
    private LocalDateTime created;

    /**
     * Идентификатор события
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    /**
     * Идентификатор пользователя, отправившего заявку
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "requester_id", referencedColumnName = "id", nullable = false)
    private User requester;

    /**
     * Статус заявки
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // todo - разобраться с этим делом - статья от ревьювера
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event  {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "app", nullable = false)
    private String app;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "event_date", nullable = false)
    @JsonFormat
    private LocalDateTime eventDate;
}

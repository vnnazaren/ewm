package ru.practicum.ewm.dto;

import lombok.*;

/**
 * Класс DTO класса получения статистики STAT
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatDto {

    private String app;

    private String uri;

    private Long hits;
}
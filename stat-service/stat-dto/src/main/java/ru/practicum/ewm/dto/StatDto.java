package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс DTO класса получения статистики STAT
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatDto {

    private String app;

    private String uri;

    private Long hits;
}
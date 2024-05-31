package ru.practicum.ewm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс получения статистики STAT
 */
@Getter
@Setter
@AllArgsConstructor
public class Stat {

    private String app;

    private String uri;

    private Long hits;
}
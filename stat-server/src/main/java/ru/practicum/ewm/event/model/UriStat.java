package ru.practicum.ewm.event.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UriStat {

    private String app;

    private String uri;

    private Integer hits;
}
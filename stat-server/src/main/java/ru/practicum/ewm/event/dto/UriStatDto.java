package ru.practicum.ewm.event.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class UriStatDto {

    private String app;

    private String uri;

    private Integer hits;
}

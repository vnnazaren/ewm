package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Широта и долгота места проведения события
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class LocationDto {

    /**
     * Широта
     */
    private float lat;

    /**
     * Долгота
     */
    private float lon;
}
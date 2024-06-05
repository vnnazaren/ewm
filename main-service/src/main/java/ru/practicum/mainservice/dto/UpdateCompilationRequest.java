package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Изменение информации о подборке событий.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UpdateCompilationRequest {

    /**
     * Список id событий подборки для полной замены текущего списка
     */
    private List<Integer> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private boolean pinned;

    /**
     * Заголовок подборки
     */
    @Size(min = 1, max = 50, message = "Длина заголовка подборки должна быть от 1 до 50 символов.")
    private String title;
}
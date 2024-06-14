package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoriesController {
    private final CategoryService categoryService;

    /**
     * Получение категорий
     *
     * @return Коллекция объектов DTO с подборками событий
     */
    @GetMapping
    public List<CategoryDto> readCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("PublicCategoriesController: GET /categories?from={}&size={}", from, size);
        return categoryService.readCategories(from, size);
    }

    /**
     * Получение информации о категории по её идентификатору
     *
     * @param catId Идентификатор подборки событий по которой необходима информация
     * @return Объект DTO подборки событий
     */
    @GetMapping("/{catId}")
    public CategoryDto readCategory(@PathVariable @Positive Long catId) {
        log.info("PublicCategoriesController: GET /categories/{}", catId);
        return categoryService.readCategory(catId);
    }
}
package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import java.util.List;

@Slf4j
@Validated
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
    public List<CategoryDto> readCategories(@RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        log.info("!!!!!!!!!!!!!!! PublicCategoriesController: GET /categories?from={}&size={}", from, size);
        return categoryService.readCategories();
    }

    /**
     * Получение информации о категории по её идентификатору
     *
     * @param catId Идентификатор подборки событий по которой необходима информация
     * @return Объект DTO подборки событий
     */
    @GetMapping("/{catId}")
    public CategoryDto readCategory(@PathVariable Long catId) {
        log.info("!!!!!!!!!!!!!!! PublicCategoriesController: GET /categories/{}", catId);
        return categoryService.readCategory(catId);
    }
}
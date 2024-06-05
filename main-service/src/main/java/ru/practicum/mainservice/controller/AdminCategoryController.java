package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.service.CategoryService;
import ru.practicum.mainservice.util.Marker;

import javax.validation.constraints.NotNull;

/**
 * Класс-контроллер CATEGORY
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    /**
     * Добавление новой категории
     *
     * @param newCategoryDto Тело запроса с DTO категории
     * @return Объект CategoryDto с новой созданной категорией
     */
    @PostMapping
    @Validated(Marker.OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody NewCategoryDto newCategoryDto) {
        log.info("POST /admin/categories - {}", newCategoryDto);
        return categoryService.createCategory(newCategoryDto);
    }

    /**
     * Изменение категории
     *
     * @param newCategoryDto Тело запроса с DTO объектом категории
     * @param catId          Идентификатор категории которую надо изменить
     * @return Объект DTO категории с изменёнными полями
     */
    @PatchMapping("/{catId}")
    @Validated(Marker.OnUpdate.class)
    public CategoryDto updateCategory(@NotNull @PathVariable long catId,
                                      @NotNull @RequestBody NewCategoryDto newCategoryDto) {
        log.info("PATCH /admin/categories/{} - {}", catId, newCategoryDto);
        return categoryService.updateCategory(catId, newCategoryDto);
    }

    /**
     * Удаление категории
     *
     * @param catId Идентификатор категории которую необходимо удалить
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        categoryService.deleteCategory(catId);
    }
}
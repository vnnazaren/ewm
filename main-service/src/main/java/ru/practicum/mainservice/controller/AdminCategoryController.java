package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * Класс-контроллер CATEGORY
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final CategoryService CategoryService;

    /**
     * Добавление новой категории
     *
     * @param newCategoryDto Тело запроса с DTO категории
     * @return Объект CategoryDto с новой созданной категорией
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("AdminCategoryController: POST /admin/categories - {}", newCategoryDto);
        return CategoryService.createCategory(newCategoryDto);
    }

    /**
     * Изменение категории
     *
     * @param newCategoryDto Тело запроса с DTO объектом категории
     * @param catId          Идентификатор категории которую надо изменить
     * @return Объект DTO категории с изменёнными полями
     */
    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("AdminCategoryController: PATCH /admin/categories/{} - {}", catId, newCategoryDto);
        return CategoryService.updateCategory(catId, newCategoryDto);
    }

    /**
     * Удаление категории
     *
     * @param catId Идентификатор категории которую необходимо удалить
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive Long catId) {
        log.info("AdminCategoryController: DELETE /admin/categories/{}", catId);
        CategoryService.deleteCategoryById(catId);
    }
}
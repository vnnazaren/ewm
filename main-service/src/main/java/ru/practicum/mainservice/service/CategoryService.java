package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса CATEGORY
 */
public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(long catId, NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    List<CategoryDto> readCategories();

    CategoryDto readCategory(long catId);
}
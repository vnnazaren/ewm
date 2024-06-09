package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса CATEGORY
 */
public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto readCategory(Long catId);

    List<CategoryDto> readCategories();

    CategoryDto updateCategory(Long catId, NewCategoryDto newCategoryDto);

    void deleteCategoryById(Long catId);
}
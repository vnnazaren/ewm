package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;

import java.util.List;

/**
 * Интерфейс класса-сервиса CATEGORY
 */
public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto readCategory(Long catId);

    List<CategoryDto> readCategories(Integer from, Integer size);

    CategoryDto updateCategory(Long catId, NewCategoryDto newCategoryDto);

    void deleteCategoryById(Long catId);
}
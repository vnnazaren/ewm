package ru.practicum.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.service.CategoryService;

import java.util.List;

/**
 * Класс-сервис CATEGORY
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(long catId, NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(long catId) {

    }

    @Override
    public List<CategoryDto> readCategories() {
        return List.of();
    }

    @Override
    public CategoryDto readCategory(long catId) {
        return null;
    }
}
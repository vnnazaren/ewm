package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.CategoryDto;
import ru.practicum.mainservice.dto.NewCategoryDto;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.exceptions.WrongParameterException;
import ru.practicum.mainservice.model.Category;
import ru.practicum.mainservice.model.mapper.CategoryMapper;
import ru.practicum.mainservice.service.CategoryService;
import ru.practicum.mainservice.storage.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        try {
            return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
        } catch (DataIntegrityViolationException e) {
            throw new WrongParameterException(String.format(
                    "Ошибка при создании пользователя %s", newCategoryDto
            ));
        }
    }

    @Override
    public CategoryDto readCategory(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException("Категория с ID " + catId + " не найдена.")));
    }

    @Override
    public List<CategoryDto> readCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long catId,
                                      NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toCategory(readCategory(catId));

        String name = newCategoryDto.getName();

        if (name != null && !name.isBlank()) {
            category.setName(name);
        }

        try {
            return CategoryMapper.toCategoryDto(categoryRepository.save(category));
        } catch (DataIntegrityViolationException e) {
            throw new WrongParameterException(String.format(
                    "Ошибка при обновлении пользователя c ID %s", category.getId()
            ));
        }
    }

    @Override
    public void deleteCategoryById(Long catId) {
        categoryRepository.deleteById(catId);
    }

}

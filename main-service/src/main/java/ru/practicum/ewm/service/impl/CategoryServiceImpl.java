package ru.practicum.ewm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exceptions.EntityNotFoundException;
import ru.practicum.ewm.exceptions.WrongParameterException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.storage.CategoryRepository;

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
                    "Ошибка при создании категории %s", newCategoryDto
            ));
        }
    }

    @Override
    public CategoryDto readCategory(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new EntityNotFoundException("Категория с ID " + catId + " не найдена.")));
    }

    @Override
    public List<CategoryDto> readCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).getContent().stream()
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
                    "Ошибка в БД при обновлении пользователя c ID %s", category.getId()
            ));
        }
    }

    @Override
    public void deleteCategoryById(Long catId) {
        categoryRepository.deleteById(catId);
    }

}

package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;

import java.util.List;

/**
 * Интерфейс класса-сервиса COMPILATION
 */
public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    CompilationDto readCompilation(Long compId);

    List<CompilationDto> readCompilations(Boolean pinned, int from, int size);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest);

    void deleteCompilation(Long compId);
}
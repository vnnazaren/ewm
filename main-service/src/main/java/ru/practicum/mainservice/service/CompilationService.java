package ru.practicum.mainservice.service;

import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;

import java.util.List;

/**
 * Интерфейс класса-сервиса COMPILATION
 */
public interface CompilationService {
    List<CompilationDto> readCompilations(Boolean pinned, int from, int size);

    CompilationDto readCompilation(long compId);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest);
}
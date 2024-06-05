package ru.practicum.mainservice.service.impl;

import org.springframework.stereotype.Service;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.service.CompilationService;

import java.util.List;

/**
 * Класс-сервис COMPILATION
 */
@Service
public class CompilationServiceImpl implements CompilationService {
    @Override
    public List<CompilationDto> readCompilations(Boolean pinned, int from, int size) {
        return List.of();
    }

    @Override
    public CompilationDto readCompilation(long compId) {
        return null;
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {

    }

    @Override
    public CompilationDto updateCompilation(long compId, UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
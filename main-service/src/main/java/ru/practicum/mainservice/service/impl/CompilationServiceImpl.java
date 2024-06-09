package ru.practicum.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.exceptions.EntityNotFoundException;
import ru.practicum.mainservice.exceptions.WrongParameterException;
import ru.practicum.mainservice.model.Compilation;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.mapper.CompilationMapper;
import ru.practicum.mainservice.service.CompilationService;
import ru.practicum.mainservice.service.EventService;
import ru.practicum.mainservice.storage.CompilationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventService eventService;

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        try {
            return CompilationMapper.toCompilationDto(compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto)));
        } catch (DataIntegrityViolationException e) {
            throw new WrongParameterException(String.format(
                    "Ошибка при создании пользователя %s", newCompilationDto
            ));
        }
    }

    @Override
    public CompilationDto readCompilation(Long compId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка с ID " + compId + " не найдена.")));
    }

    @Override
    public List<CompilationDto> readCompilations(Boolean pinned, int from, int size) {
        return compilationRepository.findAll().stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId,
                                            UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Подборка с ID " + compId + " не найдена."));

        String title = updateCompilationRequest.getTitle();
        Boolean pinned = updateCompilationRequest.getPinned();
        Set<Long> eventsIds = updateCompilationRequest.getEvents();

        if (title != null && !title.isBlank()) {
            compilation.setTitle(title);
        }

        if (pinned != null) {
            compilation.setPinned(pinned);
        }

        if (eventsIds != null) {
            Set<Event> events = eventService.readEventsByIdIn(updateCompilationRequest.getEvents());
            compilation.setEvents(events);

            // todo - тут нужно сделать сохранение в ту таблицу с составным первичным ключом
            //  проверить как оно работает!!!!!
        }

        try {
            return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
        } catch (DataIntegrityViolationException e) {
            throw new WrongParameterException(String.format(
                    "Ошибка при обновлении подборки c ID %s", compilation.getId()
            ));
        }
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }
}
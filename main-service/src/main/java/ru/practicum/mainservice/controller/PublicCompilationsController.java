package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.service.CompilationService;

import java.util.List;

/**
 * Класс-контроллер COMPILATION для публичного доступа
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationsController {
    private final CompilationService compilationService;

    /**
     * Получение подборок событий
     *
     * @return Коллекция объектов DTO с подборками событий
     */
    @GetMapping
    public List<CompilationDto> readCompilations(@RequestParam Boolean pinned,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("!!!!!!!!!!!!!!! PublicCompilationsController: GET /compilations?pinned={}&from={}&size={}", pinned, from, size);
        return compilationService.readCompilations(pinned, from, size);
    }

    /**
     * Получение подборки событий по её id
     *
     * @param compId Идентификатор подборки событий по которой необходима информация
     * @return Объект DTO подборки событий
     */
    @GetMapping("/{compId}")
    public CompilationDto readCompilation(@PathVariable Long compId) {
        log.info("!!!!!!!!!!!!!!! PublicCompilationsController: GET /compilations/{}", compId);
        return compilationService.readCompilation(compId);
    }
}
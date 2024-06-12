package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Класс-контроллер COMPILATION для публичного доступа
 */
@Slf4j
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
    public List<CompilationDto> readCompilations(@RequestParam(required = false)  Boolean pinned,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("PublicCompilationsController: GET /compilations?pinned={}&from={}&size={}", pinned, from, size);
        return compilationService.readCompilations(pinned, from, size);
    }

    /**
     * Получение подборки событий по её id
     *
     * @param compId Идентификатор подборки событий по которой необходима информация
     * @return Объект DTO подборки событий
     */
    @GetMapping("/{compId}")
    public CompilationDto readCompilation(@PathVariable @Positive Long compId) {
        log.info("PublicCompilationsController: GET /compilations/{}", compId);
        return compilationService.readCompilation(compId);
    }
}
package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.CompilationDto;
import ru.practicum.mainservice.dto.NewCompilationDto;
import ru.practicum.mainservice.dto.UpdateCompilationRequest;
import ru.practicum.mainservice.service.CompilationService;

import javax.validation.constraints.NotNull;

/**
 * Класс-контроллер COMPILATION для Администратора
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;

    /**
     * Создание новой подборки (подборка может не содержать событий)
     *
     * @param newCompilationDto Тело запроса с DTO подборки
     * @return Объект DTO с новой созданной подборкой
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @NotNull NewCompilationDto newCompilationDto) {
        log.info("!!!!!!!!!!!!!!! AdminCompilationController: POST /admin/compilations - {}", newCompilationDto);
        return compilationService.createCompilation(newCompilationDto);
    }

    /**
     * Обновление информации о подборке
     *
     * @param updateCompilationRequest Тело запроса с DTO объектом подборки
     * @param compId                   Идентификатор подборки которую надо изменить
     * @return Объект DTO подборки с изменёнными полями
     */
    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@RequestBody @NotNull UpdateCompilationRequest updateCompilationRequest,
                                            @PathVariable @NotNull Long compId) {
        log.info("!!!!!!!!!!!!!!! AdminCompilationController: PATCH /admin/compilations/{} - {}", compId, updateCompilationRequest);
        return compilationService.updateCompilation(compId, updateCompilationRequest);
    }

    /**
     * Удаление подборки
     *
     * @param compId Идентификатор подборки которую необходимо удалить
     */
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @NotNull Long compId) {
        log.info("!!!!!!!!!!!!!!! AdminCompilationController: DELETE /admin/compilations/{}", compId);
        compilationService.deleteCompilation(compId);
    }
}
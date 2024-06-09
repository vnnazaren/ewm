package ru.practicum.mainservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.EventFullDto;
import ru.practicum.mainservice.dto.EventShortDto;
import ru.practicum.mainservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final EventService eventService;

    /**
     * Получение подробной информации об опубликованном событии по его идентификатору<br/>
     * <li>событие должно быть опубликовано
     * <li>информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * <li>информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     * В случае, если события с заданным id не найдено, возвращает статус код 404
     *
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping("/{id}")
    public EventFullDto readEventById(@PathVariable Long id) {
        log.info("!!!!!!!!!!!!!!! PublicEventsController: GET /events/{}", id);
        return eventService.readEvent(id);
    }

    /**
     * Получение событий с возможностью фильтрации<br/>
     * <li>это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * <li>текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * <li>если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
     * <li>информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
     * <li>информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
     * В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список
     *
     * @param text          текст для поиска в содержимом аннотации и подробном описании события
     * @param categories    список идентификаторов категорий в которых будет вестись поиск
     * @param paid          поиск только платных/бесплатных событий
     * @param rangeStart    дата и время не раньше которых должно произойти событие
     * @param rangeEnd      дата и время не позже которых должно произойти событие
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие. Значение по умолчанию: false
     * @param sort          Вариант сортировки: по дате события или по количеству просмотров. Возможные варианты: EVENT_DATE, VIEWS
     * @param from          количество событий, которые нужно пропустить для формирования текущего набора. Значение по умолчанию: 0
     * @param size          количество событий в наборе. Значение по умолчанию: 10
     * @return Коллекция объектов DTO с пользователями
     */
    @GetMapping
    public List<EventShortDto> searchEventsByUser(@RequestParam String text,
                                                  @RequestParam List<Long> categories,
                                                  @RequestParam Boolean paid,
                                                  @RequestParam
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                  @RequestParam
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                  @RequestParam Boolean onlyAvailable,
                                                  @RequestParam(defaultValue = "event_date") String sort,
                                                  @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                  @RequestParam(defaultValue = "10") @Positive int size,
                                                  HttpServletRequest httpServletRequest) {
        log.info("!!!!!!!!!!!!!!! PublicEventsController: GET /events?text={}&categories={}&paid={}&rangeStart={}&rangeEnd={}&onlyAvailable={}&sort={}&from={}&size={}, httpServletRequest.getRequestURI()={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest.getRequestURI());
        return eventService.searchEventsByUser(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest.getRemoteAddr(), httpServletRequest.getRequestURI());
    }
}
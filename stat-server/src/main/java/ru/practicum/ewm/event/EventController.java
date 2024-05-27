package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventDtoRequest;
import ru.practicum.ewm.event.dto.UriStatDto;
import ru.practicum.ewm.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveEvent(@RequestBody EventDtoRequest eventDtoRequest) {
        log.info("POST /hit - eventDtoRequest: {}", eventDtoRequest);
        eventService.saveEvent(eventDtoRequest);
    }

    @GetMapping("/stats")
    public List<UriStatDto> getEventsInfo(@RequestParam List<String> uris,
                                          @RequestParam Boolean unique,
                                          @RequestParam LocalDateTime start,
                                          @RequestParam LocalDateTime end) {
        log.info("GET /stats - uris: {}, unique: {}, start: {}, end: {}", uris, unique, start, end);
        return eventService.getEventsInfo(uris, unique, start, end);
    }
}
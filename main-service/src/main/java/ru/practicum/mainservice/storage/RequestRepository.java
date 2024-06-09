package ru.practicum.mainservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.model.Event;
import ru.practicum.mainservice.model.Request;
import ru.practicum.mainservice.model.User;
import ru.practicum.mainservice.util.Status;

import java.util.List;

/**
 * Интерфейс класса-репозитория REQUEST
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByRequester(User user);

    List<Request> findAllByIdInAndStatus(List<Long> ids, Status status);
}
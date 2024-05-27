package ru.practicum.ewm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.client.BaseClient;
import ru.practicum.ewm.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${ewm-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createUser(UserDto userDto) {
        return post("", userDto);
    }

    public ResponseEntity<Object> readUser(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> readUsers() {
        return get("");
    }

    public ResponseEntity<Object> updateUser(long userId, UserDto userDto) {
        return patch("/" + userId, userDto);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
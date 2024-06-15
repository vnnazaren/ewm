package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс пользователя USER
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Имя или логин пользователя
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Адрес электронной почты
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
package ru.practicum.ewm.util;

import java.time.format.DateTimeFormatter;

public class Const {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final Integer HOURS_BEFORE_EVENT_ADMIN = 1;
    public static final Integer HOURS_BEFORE_EVENT_USER = 2;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
}

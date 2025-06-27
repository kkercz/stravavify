package io.github.kkercz.stravavify.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static final ZoneId TIMEZONE = ZoneId.systemDefault();

    public static LocalDateTime localDateTime(String dateString) {
        return Instant.parse(dateString)
                .atZone(TIMEZONE)
                .toLocalDateTime();
    }

    public static LocalDateTime localDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(TIMEZONE)
                .toLocalDateTime();
    }
}

package com.husen.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Created by HuSen on 2018/8/31 17:01.
 */
@SuppressWarnings("ALL")
public class DateTimeFormatterUtil {
    public static final String ONE_MATTER = "yyyy-MM-dd HH:mm:ss";

    public static String formatter(LocalDateTime localDateTime, String matter) {
        return DateTimeFormatter.ofPattern(matter).format(localDateTime);
    }

    public static long toEpochSecond(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }
}

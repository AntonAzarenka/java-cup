package com.azarenka.jc.service.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

/**
 * Time utils.
 *
 * Date: 21.07.2019
 *
 * @author Anton Azarenka
 */
@Component
public class TimeUtil {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    private static final Map<String, Month> MONTHS = new HashMap<>();

    public static String timeToString(LocalDate ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    @PostConstruct
    public void init() {
        MONTHS.put("Январь", Month.JANUARY);
        MONTHS.put("Февраль", Month.FEBRUARY);
        MONTHS.put("Март", Month.MARCH);
        MONTHS.put("Апрель", Month.APRIL);
        MONTHS.put("Май", Month.MAY);
        MONTHS.put("Июнь", Month.JUNE);
        MONTHS.put("Июль", Month.JULY);
        MONTHS.put("Август", Month.AUGUST);
        MONTHS.put("Сентярь", Month.SEPTEMBER);
        MONTHS.put("Отябрь", Month.OCTOBER);
        MONTHS.put("Ноябрь", Month.NOVEMBER);
        MONTHS.put("Декабрь", Month.DECEMBER);
    }

    /**
     * Formats {@link LocalDate} to string.
     *
     * @param date date
     * @return month
     */
    public static String getMonth(LocalDate date) {
        Month month = date.getMonth();
        return MONTHS.entrySet()
            .stream()
            .filter(pair -> pair.getValue().equals(month))
            .findFirst()
            .map(Entry::getKey)
            .orElse(null);
    }

    /**
     * Returns month based on year and number of month.
     *
     * @param year  year
     * @param month number of month
     * @return month
     */
    public static String getMonth(String year, String month) {
        return getMonth(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1));
    }

    /**
     * Converts day of week to string.
     *
     * @param dayOfWeek day of week
     * @return day of week
     */
    public static String dayToString(DayOfWeek dayOfWeek) {
        char[] arrayDay = dayOfWeek.toString().toLowerCase().toCharArray();
        arrayDay[0] = String.valueOf(arrayDay[0]).toUpperCase().charAt(0);

        return IntStream.range(0, arrayDay.length)
            .mapToObj(i -> String.valueOf(arrayDay[i]))
            .collect(Collectors.joining());
    }
}

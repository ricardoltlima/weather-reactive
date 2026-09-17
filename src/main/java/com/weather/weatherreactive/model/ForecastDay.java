package com.weather.weatherreactive.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ForecastDay {
    TODAY("Today"),
    TONIGHT("Tonight"),
    MONDAY("Monday"),
    MONDAY_NIGHT("Monday Night"),
    TUESDAY("Tuesday"),
    TUESDAY_NIGHT("Tuesday Night"),
    WEDNESDAY("Wednesday"),
    WEDNESDAY_NIGHT("Wednesday Night"),
    THURSDAY("Thursday"),
    THURSDAY_NIGHT("Thursday Night"),
    FRIDAY("Friday"),
    FRIDAY_NIGHT("Friday Night"),
    SATURDAY("Saturday"),
    SATURDAY_NIGHT("Saturday Night"),
    SUNDAY("Sunday"),
    SUNDAY_NIGHT("Sunday Night");

    private final String label;

    ForecastDay(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static Optional<ForecastDay> from(String value) {
        return Arrays.stream(values())
                .filter(day -> day.label.equalsIgnoreCase(value))
                .findFirst();
    }

    public static String validValues() {
        return Arrays.stream(values())
                .map(ForecastDay::label)
                .collect(Collectors.joining(", "));
    }
}

package com.weather.weatherreactive.dto;

import java.util.List;

public record WeatherResponse(
        Properties properties
) {
    public record Properties(
            List<Period> periods
    ) {
    }

    public record Period(
            String name,
            int temperature,
            String temperatureUnit,
            String shortForecast
    ) {
    }
}

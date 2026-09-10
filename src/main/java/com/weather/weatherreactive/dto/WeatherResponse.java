package com.weather.weatherreactive.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        Properties properties
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Properties(
            List<Period> periods
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Period(
            String name,
            int temperature,
            String temperatureUnit,
            String shortForecast
    ) {
    }
}

package com.weather.weatherreactive.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ForecastResponse(
        List<DailyForecast> daily
) {
    public record DailyForecast(
            @JsonProperty("day_name")
            String dayName,
            @JsonProperty("temp_high_celsius")
            double tempHighCelsius,
            @JsonProperty("forecast_blurp")
            String forecastBlurp
    ) {
    }
}

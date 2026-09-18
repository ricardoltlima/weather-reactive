package com.weather.weatherreactive.mapper;

import com.weather.weatherreactive.dto.ForecastResponse;
import com.weather.weatherreactive.dto.WeatherResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(target = "dayName", source = "name")
    @Mapping(target = "tempHighCelsius", expression = "java(toCelsius(period.temperature(), period.temperatureUnit()))")
    @Mapping(target = "forecastBlurp", source = "shortForecast")
    ForecastResponse.DailyForecast toDailyForecast(WeatherResponse.Period period);

    default double toCelsius(double temperature, String unit) {
        if ("C".equalsIgnoreCase(unit)) {
            return temperature;
        }

        return Math.round((temperature - 32) * 5.0 / 9.0 * 10.0) / 10.0;
    }
}

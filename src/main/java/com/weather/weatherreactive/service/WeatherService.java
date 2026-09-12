package com.weather.weatherreactive.service;

import com.weather.weatherreactive.client.WeatherClient;
import com.weather.weatherreactive.dto.ForecastResponse;
import com.weather.weatherreactive.error.InvalidDateException;
import com.weather.weatherreactive.mapper.WeatherMapper;
import com.weather.weatherreactive.model.ForecastDay;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WeatherService {

    private final WeatherClient client;
    private final WeatherMapper mapper;

    public WeatherService(WeatherClient client, WeatherMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public Mono<ForecastResponse> getDailyForecast(String day) {
        Optional<ForecastDay> parsedDay = validateDay(day);
        if (parsedDay.isEmpty()) {
            return Mono.error(new InvalidDateException());
        }

        ForecastDay forecastDay = parsedDay.get();
        log.info("Getting daily forecast for day: {}", forecastDay.label());

        return client.getDailyForecast()
                .map(response -> {
                    List<ForecastResponse.DailyForecast> daily = response.properties().periods().stream()
                            .filter(period -> period.name().equalsIgnoreCase(forecastDay.label()))
                            .map(mapper::toDailyForecast)
                            .toList();

                    log.info("Returning {} ", daily);

                    return new ForecastResponse(daily);
                });
    }

    private Optional<ForecastDay> validateDay(String day) {
        if (StringUtils.isBlank(day)) {
            return Optional.empty();
        }

        return ForecastDay.from(day.trim());
    }
}

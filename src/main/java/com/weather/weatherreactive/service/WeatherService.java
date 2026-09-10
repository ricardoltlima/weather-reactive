package com.weather.weatherreactive.service;

import com.weather.weatherreactive.client.WeatherClient;
import com.weather.weatherreactive.dto.ForecastResponse;
import com.weather.weatherreactive.mapper.WeatherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
        log.info("Getting daily forecast for day: {}", day);

        return client.getDailyForecast()
                .map(response -> {
                    List<ForecastResponse.DailyForecast> daily = response.properties().periods().stream()
                            .filter(period -> period.name().equalsIgnoreCase(day))
                            .map(mapper::toDailyForecast)
                            .toList();

                    log.info("Returning {} forecast periods", daily.size());

                    return new ForecastResponse(daily);
                });
    }
}

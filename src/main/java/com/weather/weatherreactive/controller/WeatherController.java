package com.weather.weatherreactive.controller;

import com.weather.weatherreactive.dto.ForecastResponse;
import com.weather.weatherreactive.service.WeatherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/forecast")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    public Mono<ForecastResponse> getDailyForecast(String day) {
        return service.getDailyForecast(day);
    }
}

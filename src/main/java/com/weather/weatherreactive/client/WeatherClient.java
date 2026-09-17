package com.weather.weatherreactive.client;

import com.weather.weatherreactive.dto.WeatherResponse;
import com.weather.weatherreactive.error.WeatherApiTimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Component
public class WeatherClient {

    private final String forecastUrl;
    private final Duration requestTimeout;
    private final WebClient webClient;

    public WeatherClient(
            @Value("${weather.forecast.url}") String forecastUrl,
            @Value("${weather.request.timeout:5s}") Duration requestTimeout,
            WebClient.Builder webClientBuilder
    ) {
        this.forecastUrl = forecastUrl;
        this.requestTimeout = requestTimeout;
        this.webClient = webClientBuilder
                .build();
    }

    public Mono<WeatherResponse> getDailyForecast() {
        return webClient.get()
                .uri(forecastUrl)
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .timeout(requestTimeout)
                .onErrorMap(TimeoutException.class, exception -> new WeatherApiTimeoutException());
    }
}

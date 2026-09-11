package com.weather.weatherreactive.client;

import com.weather.weatherreactive.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WeatherClient {

    private final String forecastUrl;
    private final WebClient webClient;

    public WeatherClient(
            @Value("${weather.forecast.url}") String forecastUrl,
            WebClient.Builder webClientBuilder
    ) {
        this.forecastUrl = forecastUrl;
        this.webClient = webClientBuilder
                .build();
    }

    public Mono<WeatherResponse> getDailyForecast() {
        return webClient.get()
                .uri(forecastUrl)
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
}

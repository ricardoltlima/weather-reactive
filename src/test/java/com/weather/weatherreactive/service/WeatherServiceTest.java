package com.weather.weatherreactive.service;

import com.weather.weatherreactive.client.WeatherClient;
import com.weather.weatherreactive.dto.ForecastResponse;
import com.weather.weatherreactive.dto.WeatherResponse;
import com.weather.weatherreactive.mapper.WeatherMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherServiceTest {

    private final WeatherClient client = mock(WeatherClient.class);
    private final WeatherMapper mapper = Mappers.getMapper(WeatherMapper.class);
    private final WeatherService service = new WeatherService(client, mapper);

    @Test
    void getDailyForecastReturnsMatchingDayForecast() {
        when(client.getDailyForecast()).thenReturn(Mono.just(new WeatherResponse(
                new WeatherResponse.Properties(List.of(
                        new WeatherResponse.Period("Sunday", 81, "F", "Partly Sunny"),
                        new WeatherResponse.Period("Monday", 82, "F", "Mostly Sunny")
                ))
        )));

        Mono<ForecastResponse> result = service.getDailyForecast("Monday");

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.daily()).hasSize(1);

                    ForecastResponse.DailyForecast forecast = response.daily().get(0);
                    assertThat(forecast.dayName()).isEqualTo("Monday");
                    assertThat(forecast.tempHighCelsius()).isEqualTo(27.8);
                    assertThat(forecast.forecastBlurp()).isEqualTo("Mostly Sunny");
                })
                .verifyComplete();
    }

    @Test
    void getDailyForecastReturnsEmptyListWhenDayDoesNotMatch() {
        when(client.getDailyForecast()).thenReturn(Mono.just(new WeatherResponse(
                new WeatherResponse.Properties(List.of(
                        new WeatherResponse.Period("Sunday", 81, "F", "Partly Sunny")
                ))
        )));

        Mono<ForecastResponse> result = service.getDailyForecast("Monday");

        StepVerifier.create(result)
                .assertNext(response -> assertThat(response.daily()).isEmpty())
                .verifyComplete();
    }

    @Test
    void getDailyForecastPropagatesClientErrors() {
        RuntimeException exception = new RuntimeException("Weather API unavailable");
        when(client.getDailyForecast()).thenReturn(Mono.error(exception));

        Mono<ForecastResponse> result = service.getDailyForecast("Monday");

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> assertThat(error).isSameAs(exception))
                .verify();
    }
}

package com.weather.weatherreactive.error;

public class WeatherApiTimeoutException extends RuntimeException {
    public WeatherApiTimeoutException() {
        super("Weather API request timed out");
    }
}

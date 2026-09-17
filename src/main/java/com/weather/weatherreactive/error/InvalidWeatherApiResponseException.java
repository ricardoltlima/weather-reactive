package com.weather.weatherreactive.error;

public class InvalidWeatherApiResponseException extends RuntimeException {

    public InvalidWeatherApiResponseException() {
        super("Weather API returned an invalid response");
    }
}

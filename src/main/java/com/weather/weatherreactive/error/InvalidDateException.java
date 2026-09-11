package com.weather.weatherreactive.error;

import com.weather.weatherreactive.service.ForecastDay;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        super("Invalid date. Valid values are: " + ForecastDay.validValues());
    }
}

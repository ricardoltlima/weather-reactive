package com.weather.weatherreactive.exception;

import com.weather.weatherreactive.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleWebClientResponseException(WebClientResponseException exception) {
        HttpStatus status = HttpStatus.resolve(exception.getStatusCode().value());
        log.error("Weather API responded with status {}", exception.getStatusCode(), exception);

        return buildResponse(
                status != null ? status : HttpStatus.BAD_GATEWAY,
                "WEATHER_API_ERROR",
                "Weather API request failed"
        );
    }

    @ExceptionHandler(WebClientRequestException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleWebClientRequestException(WebClientRequestException exception) {
        log.error("Weather API request failed", exception);

        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                "WEATHER_API_UNAVAILABLE",
                "Weather API is unavailable"
        );
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleServerWebInputException(ServerWebInputException exception) {
        log.error("Invalid request", exception);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                "Invalid request"
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(Exception exception) {
        log.error("Unexpected error", exception);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "Unexpected error"
        );
    }

    private Mono<ResponseEntity<ErrorResponse>> buildResponse(HttpStatus status, String code, String message) {
        return Mono.just(ResponseEntity
                .status(status)
                .body(new ErrorResponse(code, message, Instant.now())));
    }
}

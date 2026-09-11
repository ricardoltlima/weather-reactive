package com.weather.weatherreactive.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private static final String INVALID_DATE_MESSAGE = "Invalid date. Valid values are: Today, Monday, Monday Night, "
            + "Tuesday, Tuesday Night, Wednesday, Wednesday Night, Thursday, Thursday Night, Friday, Friday Night, "
            + "Saturday, Saturday Night, Sunday, Sunday Night";

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInvalidDateExceptionReturnsBadRequestMessage() {
        Mono<ResponseEntity<ErrorResponse>> result = handler.handleInvalidDateException(new InvalidDateException());

        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody().code()).isEqualTo("INVALID_DATE");
                    assertThat(response.getBody().message()).isEqualTo(INVALID_DATE_MESSAGE);
                })
                .verifyComplete();
    }
}

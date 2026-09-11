# Weather Reactive

Reactive Spring Boot API that returns a simplified daily forecast from the National Weather Service forecast endpoint.

## Requirements

- Java 17
- Maven wrapper included in this repository

## Tools

- Spring Boot
- Spring WebFlux
- Spring WebClient
- Reactor
- MapStruct
- Lombok
- Apache Commons Lang
- JUnit 5
- Mockito
- AssertJ

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts on the default Spring Boot port, `8080`.

## API

```http
GET /api/v1/forecast?day=Monday
```

The `day` query parameter is required. Valid values are:

- `Today`
- `Monday`
- `Monday Night`
- `Tuesday`
- `Tuesday Night`
- `Wednesday`
- `Wednesday Night`
- `Thursday`
- `Thursday Night`
- `Friday`
- `Friday Night`
- `Saturday`
- `Saturday Night`
- `Sunday`
- `Sunday Night`

Example response:

```json
{
  "daily": [
    {
      "day_name": "Monday",
      "temp_high_celsius": 27.8,
      "forecast_blurp": "Mostly Sunny"
    }
  ]
}
```

Invalid `day` values return `400 BAD_REQUEST` with a message listing the allowed values.

## Test

```powershell
.\mvnw.cmd test
```

## Postman

Postman files are available in the `postman` directory:

- `weather-reactive.postman_collection.json`
- `weather-reactive.postman_environment.json`

Import both files into Postman, select the `Weather Reactive - Local` environment, and start the application before running the collection.

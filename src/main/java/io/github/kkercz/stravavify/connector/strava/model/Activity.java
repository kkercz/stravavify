package io.github.kkercz.stravavify.connector.strava.model;

import java.time.Duration;
import java.time.LocalDateTime;

public record Activity(
        Long id,
        LocalDateTime startTime,
        Duration duration,
        String description
) {}

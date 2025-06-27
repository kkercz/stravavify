package io.github.kkercz.stravavify.connector.spotify.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public record Song(
        String title,
        List<String> artists,
        String album,
        LocalDateTime playedAt,
        Duration duration
) {}

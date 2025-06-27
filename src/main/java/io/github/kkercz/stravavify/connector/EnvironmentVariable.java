package io.github.kkercz.stravavify.connector;

import java.util.Optional;

public enum EnvironmentVariable {
    SPOTIFY_CLIENT_ID,
    SPOTIFY_CLIENT_SECRET,
    SPOTIFY_REFRESH_TOKEN,

    STRAVA_CLIENT_ID,
    STRAVA_CLIENT_SECRET,
    STRAVA_REFRESH_TOKEN;

    public String get() {
        return Optional
                .ofNullable(System.getenv(this.name()))
                .orElseThrow(() -> new IllegalStateException("Variable " + name() + " must be defined in the environment"));
    }
}

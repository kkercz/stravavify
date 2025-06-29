package io.github.kkercz.stravavify.connector;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public enum EnvironmentVariable {

    MUSIC_PLAYER, // spotify / last.fm

    SPOTIFY_CLIENT_ID,
    SPOTIFY_CLIENT_SECRET,
    SPOTIFY_REFRESH_TOKEN,

    LAST_FM_USER,
    LAST_FM_API_KEY,

    STRAVA_CLIENT_ID,
    STRAVA_CLIENT_SECRET,
    STRAVA_REFRESH_TOKEN;

    public String get() {
        return getOr(null);
    }

    public String getOr(@Nullable String defaultValue) {
        return Optional
                .ofNullable(
                        Optional.ofNullable(System.getenv(this.name())).orElse(defaultValue))
                .orElseThrow(() -> new IllegalStateException("Variable " + name() + " must be defined in the environment"));
    }
}

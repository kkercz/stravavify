package io.github.kkercz.stravavify.connector;

public enum EnvironmentVariable {
    SPOTIFY_CLIENT_ID,
    SPOTIFY_CLIENT_SECRET,
    SPOTIFY_REFRESH_TOKEN,

    STRAVA_CLIENT_ID,
    STRAVA_CLIENT_SECRET,
    STRAVA_REFRESH_TOKEN;

    public String get() {
        return System.getenv(this.name());
    }
}

package io.github.kkercz.stravavify.connector;

import io.github.kkercz.stravavify.connector.lastfm.LastFmConnector;
import io.github.kkercz.stravavify.connector.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.connector.strava.StravaConnector;
import io.github.kkercz.stravavify.connector.strava.api.StravaApi;
import io.github.kkercz.stravavify.connector.strava.api.model.RefreshTokenRequest;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_REFRESH_TOKEN;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_REFRESH_TOKEN;
import static java.util.stream.Collectors.joining;

public class ConnectorFactory {

    private enum MusicPlayerType {
        SPOTIFY("spotify", ConnectorFactory::spotifyConnector),
        LAST_FM("last.fm", ConnectorFactory::lastFmConnector);

        private final String name;
        private final Function<ConnectorFactory, MusicPlayer> creator;

        public static MusicPlayerType defaultType() {
            return SPOTIFY;
        }

        MusicPlayerType(String name, Function<ConnectorFactory, MusicPlayer> creator) {
            this.name = name;
            this.creator = creator;
        }

        public String getName() {
            return name;
        }

        public Function<ConnectorFactory, MusicPlayer> getCreator() {
            return creator;
        }
    }

    public MusicPlayer musicPlayer() {
        String playerEnvVariable = EnvironmentVariable.MUSIC_PLAYER.getOr(MusicPlayerType.defaultType().getName());

        return Stream.of(MusicPlayerType.values())
                .filter(player -> player.getName().equalsIgnoreCase(playerEnvVariable))
                .findFirst()
                .stream()
                .peek(selectedPlayer -> System.out.println(
                        "Based on the configuration, songs will be fetched from: " + selectedPlayer.getName()))
                .findFirst()
                .map(mpt -> mpt.getCreator().apply(this))
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "Invalid value of the 'MUSIC_PLAYER' variable. Allowed values: %s. Given: %s",
                        Stream.of(MusicPlayerType.values()).map(MusicPlayerType::getName).collect(joining(", ")),
                        playerEnvVariable)));
    }

    public SpotifyConnector spotifyConnector() {
        try {
            SpotifyApi spotifyApi = SpotifyApi.builder()
                    .setClientId(SPOTIFY_CLIENT_ID.get())
                    .setClientSecret(SPOTIFY_CLIENT_SECRET.get())
                    .setRefreshToken(SPOTIFY_REFRESH_TOKEN.get())
                    .build();

            AuthorizationCodeCredentials token = spotifyApi.authorizationCodeRefresh()
                    .build()
                    .execute();

            spotifyApi.setAccessToken(token.getAccessToken());

            return new SpotifyConnector(spotifyApi);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public LastFmConnector lastFmConnector() {
        return new LastFmConnector(
                EnvironmentVariable.LAST_FM_USER.get(),
                EnvironmentVariable.LAST_FM_API_KEY.get());
    }

    public StravaConnector stravaConnector() throws IOException {
        StravaApi.Authentication stravaApi = StravaApi.Authentication.create();
        String token = stravaApi
                .refreshToken(new RefreshTokenRequest(
                        STRAVA_CLIENT_ID.get(),
                        STRAVA_CLIENT_SECRET.get(),
                        "refresh_token",
                        STRAVA_REFRESH_TOKEN.get()))
                .execute().body().access_token();

        return new StravaConnector(StravaApi.create(token));
    }
}

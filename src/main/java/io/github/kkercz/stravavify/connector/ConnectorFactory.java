package io.github.kkercz.stravavify.connector;

import io.github.kkercz.stravavify.connector.lastfm.LastFmConnector;
import io.github.kkercz.stravavify.connector.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.connector.strava.StravaConnector;
import io.github.kkercz.stravavify.connector.strava.api.StravaApi;
import io.github.kkercz.stravavify.connector.strava.api.model.RefreshTokenRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.io.IOException;

import static io.github.kkercz.stravavify.connector.EnvironmentVariable.MUSIC_PLAYER;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_REFRESH_TOKEN;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_REFRESH_TOKEN;

public class ConnectorFactory {

    public MusicPlayer musicPlayer() throws Exception {
        String musicPlayer = EnvironmentVariable.MUSIC_PLAYER.getOr("spotify");
        if (musicPlayer.equalsIgnoreCase("spotify")) {
            System.out.println("Fetching recent songs from Spotify");
            return spotifyConnector();
        } else if (musicPlayer.equalsIgnoreCase("last.fm")) {
            System.out.println("Fetching recent songs from last.fm");
            return lastFmConnector();
        } else {
            throw new IllegalStateException("Invalid value of the 'MUSIC_PLAYER' variable. Allowed values: 'spotify', 'last.fm'. Given: " + MUSIC_PLAYER.get());
        }
    }

    public SpotifyConnector spotifyConnector() throws IOException, ParseException, SpotifyWebApiException {
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

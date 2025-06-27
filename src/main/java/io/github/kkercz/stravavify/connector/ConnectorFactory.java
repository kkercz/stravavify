package io.github.kkercz.stravavify.connector;

import io.github.kkercz.stravavify.connector.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.connector.strava.StravaConnector;
import io.github.kkercz.stravavify.connector.strava.api.StravaApi;
import io.github.kkercz.stravavify.connector.strava.api.model.RefreshTokenRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import java.io.IOException;

import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.SPOTIFY_REFRESH_TOKEN;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_ID;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_CLIENT_SECRET;
import static io.github.kkercz.stravavify.connector.EnvironmentVariable.STRAVA_REFRESH_TOKEN;

public class ConnectorFactory {


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

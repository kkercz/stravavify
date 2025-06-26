package io.github.kkercz.stravavify.spotify;

import io.github.kkercz.stravavify.model.Song;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class SpotifyConnector {

    private final SpotifyApi spotifyApi = SpotifyApi.builder()
            .setClientId(System.getenv("SPOTIFY_CLIENT_ID"))
            .setClientSecret(System.getenv("SPOTIFY_CLIENT_SECRET"))
            .setRefreshToken(System.getenv("SPOTIFY_REFRESH_TOKEN"))
            .build();

    {
        try {
            initAccessToken();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public List<Song> getSongs() throws IOException, ParseException, SpotifyWebApiException {
        PagingCursorbased<PlayHistory> playHistory = spotifyApi
                .getCurrentUsersRecentlyPlayedTracks()
                .build()
                .execute();

        return Stream.of(playHistory.getItems())
                .map(ph -> toSong(ph.getTrack()))
                .toList();
    }

    private Song toSong(Track track) {
        return new Song(
                track.getName(),
                Stream.of(track.getArtists()).map(ArtistSimplified::getName).toList(),
                track.getAlbum().getName());
    }

    private void initAccessToken() throws IOException, SpotifyWebApiException, ParseException {
        AuthorizationCodeCredentials token = spotifyApi.authorizationCodeRefresh()
                .build()
                .execute();

        spotifyApi.setAccessToken(token.getAccessToken());
    }
}

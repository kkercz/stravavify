package io.github.kkercz.stravavify.connector.spotify;

import io.github.kkercz.stravavify.connector.MusicPlayer;
import io.github.kkercz.stravavify.model.Song;
import io.github.kkercz.stravavify.util.DateUtils;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static io.github.kkercz.stravavify.model.TimeBounds.timeBounds;

public class SpotifyConnector implements MusicPlayer {

    private final SpotifyApi spotifyApi;

    public SpotifyConnector(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
    }

    @Override
    public List<Song> getRecentSongs() throws Exception {
        PagingCursorbased<PlayHistory> playHistory = spotifyApi
                .getCurrentUsersRecentlyPlayedTracks()
                .build()
                .execute();

        return Stream.of(playHistory.getItems())
                .map(this::toSong)
                .sorted(Comparator.comparing(Song::time))
                .toList();
    }

    private Song toSong(PlayHistory playHistory) {
        Track track = playHistory.getTrack();
        Duration trackDuration = Duration.ofMillis(track.getDurationMs());

        return new Song(
                track.getName(),
                Stream.of(track.getArtists()).map(ArtistSimplified::getName).toList(),
                track.getAlbum().getName(),
                timeBounds(
                        DateUtils.localDateTime(playHistory.getPlayedAt()).minus(trackDuration),
                        trackDuration)
        );
    }
}

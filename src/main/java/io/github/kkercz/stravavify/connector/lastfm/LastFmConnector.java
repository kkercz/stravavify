package io.github.kkercz.stravavify.connector.lastfm;

import de.umass.lastfm.Caller;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import io.github.kkercz.stravavify.connector.MusicPlayer;
import io.github.kkercz.stravavify.model.Song;
import io.github.kkercz.stravavify.model.TimeBounds;
import io.github.kkercz.stravavify.util.DateUtils;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

public class LastFmConnector implements MusicPlayer {

    private final String user;
    private final String apiKey;

    public LastFmConnector(String user, String apiKey) {
        this.user = user;
        this.apiKey = apiKey;

        Caller.getInstance().setUserAgent("io.github.kkercz/stravavify");
        Caller.getInstance().getLogger().setLevel(Level.WARNING);
    }

    @Override
    public List<Song> getRecentSongs() throws Exception {
        PaginatedResult<Track> tracks = User.getRecentTracks(user, apiKey);

        return tracks.getPageResults().stream()
                .map(t -> new Song(
                        t.getName(),
                        List.of(t.getArtist()),
                        t.getAlbum(),
                        TimeBounds.timeBounds(
                                DateUtils.localDateTime(t.getPlayedWhen()),
                                t.getDuration() > 0 ? Duration.ofSeconds(t.getDuration()) : Duration.ofMinutes(10))))
                .sorted(Comparator.comparing(Song::time))
                .toList();
    }
}

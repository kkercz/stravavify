package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.connector.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.connector.spotify.model.Song;
import io.github.kkercz.stravavify.connector.strava.StravaConnector;
import io.github.kkercz.stravavify.connector.strava.model.Activity;

import java.util.List;
import java.util.Optional;

public class Stravavify {
    private final SpotifyConnector spotifyConnector;
    private final StravaConnector stravaConnector;

    public Stravavify(SpotifyConnector spotifyConnector, StravaConnector stravaConnector) {
        this.spotifyConnector = spotifyConnector;
        this.stravaConnector = stravaConnector;
    }

    public void updateDescriptionWithSongs() throws Exception {

        List<Song> recentSongs = spotifyConnector.getRecentSongs();
        Optional<Activity> latestActivityWithEmptyDescription = stravaConnector
                .getLatestActivity()
                .filter(a -> a.description() != null && !a.description().isBlank());

        if (latestActivityWithEmptyDescription.isEmpty()) {
            return;
        }

        Activity activity = latestActivityWithEmptyDescription.get();


        System.out.println("Done and done");
    }
}

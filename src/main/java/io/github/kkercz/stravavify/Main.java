package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.model.Song;
import io.github.kkercz.stravavify.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.strava.StravaConnector;

import static java.util.stream.Collectors.joining;

public class Main {
    public static void main(String[] args) {
        try {
            SpotifyConnector spotifyConnector = new SpotifyConnector();

            System.out.println(spotifyConnector.getSongs().stream().map(Song::toString).collect(joining("\n")));

            StravaConnector stravaConnector = new StravaConnector();
            stravaConnector.getLatestActivity();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
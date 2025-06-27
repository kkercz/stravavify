package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.connector.ConnectorFactory;
import io.github.kkercz.stravavify.connector.spotify.SpotifyConnector;
import io.github.kkercz.stravavify.connector.spotify.model.Song;
import io.github.kkercz.stravavify.connector.strava.StravaConnector;

import static java.util.stream.Collectors.joining;

public class Main {
    public static void main(String[] args) {
        try {
            ConnectorFactory connectorFactory = new ConnectorFactory();
            SpotifyConnector spotifyConnector = connectorFactory.spotifyConnector();

            System.out.println(spotifyConnector.getSongs().stream().map(Song::toString).collect(joining("\n")));

            StravaConnector stravaConnector = connectorFactory.stravaConnector();
            stravaConnector.getLatestActivity();

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
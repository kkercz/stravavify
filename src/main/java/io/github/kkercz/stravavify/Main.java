package io.github.kkercz.stravavify;

import io.github.kkercz.stravavify.model.Song;

import static java.util.stream.Collectors.joining;

public class Main {
    public static void main(String[] args) {
        try {
            SpotifyConnector spotifyConnector = new SpotifyConnector();

            System.out.print(spotifyConnector.getSongs().stream().map(Song::toString).collect(joining("\n")));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
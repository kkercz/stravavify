package io.github.kkercz.stravavify.connector;

import io.github.kkercz.stravavify.model.Song;

import java.util.List;

public interface MusicPlayer {
    List<Song> getRecentSongs() throws Exception;
}

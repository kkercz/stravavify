package io.github.kkercz.stravavify.connector.spotify.model;

import java.util.List;

import static io.github.kkercz.stravavify.StringUtils.cleanUp;
import static io.github.kkercz.stravavify.StringUtils.joinWithLimit;

public record Song(String title, List<String> artists, String album) {

    /**
     * @return 🎵 Non-Stop 🎤 Leslie Odom Jr., Lin-Manuel Miranda, ... 💿 Hamilton
     */
    @Override
    public String toString() {
        return String.format(
                "\uD83C\uDFB5 %s \uD83C\uDFA4 %s \uD83D\uDCBF %s",
                cleanUp(title),
                joinWithLimit(artists),
                cleanUp(album));
    }
}

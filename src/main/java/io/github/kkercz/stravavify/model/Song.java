package io.github.kkercz.stravavify.model;

import java.util.List;
import java.util.stream.Collectors;

public record Song(String title, List<String> artists, String album) {

    /**
     * @return Non-Stop - 🎤 Leslie Odom Jr., Lin-Manuel Miranda, Renée Elise Goldsberry, ... - 💿 Hamilton
     */
    @Override
    public String toString() {
        return String.format(
                "\uD83C\uDFB5 %s - \uD83C\uDFA4 %s - \uD83D\uDCBF %s",
                cleanUp(title),
                artists.stream().limit(3).collect(Collectors.joining(", ")) + (artists.size() > 3 ? ", ..." : ""),
                cleanUp(album));
    }

    private String cleanUp(String name) {
        return name
                .replaceAll("\\s*\\(.*\\)", "")
                .replaceAll("\\s+-.*$", "");
    }
}

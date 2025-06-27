package io.github.kkercz.stravavify.model;

import java.util.List;

public record Song(
        String title,
        List<String> artists,
        String album,
        TimeBounds time
) {}

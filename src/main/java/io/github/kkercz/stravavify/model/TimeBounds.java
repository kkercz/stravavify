package io.github.kkercz.stravavify.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeBounds(LocalDateTime start, LocalDateTime end) implements Comparable<TimeBounds> {

    public static TimeBounds timeBounds(LocalDateTime start, Duration duration) {
        return new TimeBounds(start, start.plus(duration));
    }

    public boolean intersects(TimeBounds other) {
        return start.isAfter(other.start()) && start.isBefore(other.end()) ||
                end.isAfter(other.start()) && end.isBefore(other.end());
    }

    @Override
    public int compareTo(@NotNull TimeBounds o) {
        return start.compareTo(o.start());
    }
}

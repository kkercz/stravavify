package io.github.kkercz.stravavify.model;

public record Activity(
        Long id,
        TimeBounds time,
        String description
) {}

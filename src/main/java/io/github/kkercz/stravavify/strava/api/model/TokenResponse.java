package io.github.kkercz.stravavify.strava.api.model;

public record TokenResponse(
        String access_token,
        String token_type,
        String expires_at,
        String expires_in,
        String refresh_token
) {}

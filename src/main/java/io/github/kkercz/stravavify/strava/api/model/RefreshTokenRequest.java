package io.github.kkercz.stravavify.strava.api.model;

public record RefreshTokenRequest(
        String client_id,
        String client_secret,
        String grant_type,
        String refresh_token
) {}

package io.github.kkercz.stravavify.strava;

import io.github.kkercz.stravavify.strava.api.StravaApi;
import io.github.kkercz.stravavify.strava.api.model.RefreshTokenRequest;

public class StravaConnector {

    private final StravaApi stravaApi = StravaApi.create();
    private final String token;
    {
        try {
            token = stravaApi
                    .refreshToken(new RefreshTokenRequest(
                            System.getenv("STRAVA_CLIENT_ID"),
                            System.getenv("STRAVA_CLIENT_SECRET"),
                            "refresh_token",
                            System.getenv("STRAVA_REFRESH_TOKEN")))
                    .execute().body().access_token();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getLatestActivity() {
        // not doing anything for now
    }

}

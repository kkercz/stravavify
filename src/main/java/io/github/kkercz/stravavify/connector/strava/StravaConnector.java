package io.github.kkercz.stravavify.connector.strava;

import io.github.kkercz.stravavify.connector.strava.api.StravaApi;
import io.github.kkercz.stravavify.connector.strava.model.Activity;

import java.util.Optional;

public class StravaConnector {

    private final StravaApi stravaApi;
    private final String token;

    public StravaConnector(StravaApi stravaApi, String token) {
        this.stravaApi = stravaApi;
        this.token = token;
    }

    public Optional<Activity> getLatestActivity() {
        return Optional.empty();
    }

    public void updateDescription(Long activityId, String description) {

    }

}

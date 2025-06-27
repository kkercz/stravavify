package io.github.kkercz.stravavify.connector.strava;

import io.github.kkercz.stravavify.connector.strava.api.StravaApi;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivityDetailed;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivitySimple;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivityUpdate;
import io.github.kkercz.stravavify.model.Activity;
import io.github.kkercz.stravavify.util.DateUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.kkercz.stravavify.model.TimeBounds.timeBounds;

public class StravaConnector {

    private final StravaApi stravaApi;

    public StravaConnector(StravaApi stravaApi) {
        this.stravaApi = stravaApi;
    }

    public Optional<Activity> getLatestActivity(Duration from) throws IOException {

        long afterEpoch = Instant.now().getEpochSecond() - from.toSeconds();

        List<ActivitySimple> activities = stravaApi.getLatestActivities(afterEpoch).execute().body();

        Optional<Long> latestActivityId = Objects.requireNonNull(activities)
                .stream()
                .findFirst()
                .map(ActivitySimple::id);

        if (latestActivityId.isEmpty()) {
            return Optional.empty();
        }

        ActivityDetailed a = Objects.requireNonNull(
                stravaApi.getActivity(latestActivityId.get().toString()).execute().body());

        return Optional.of(new Activity(
                a.id(),
                timeBounds(
                        DateUtils.localDateTime(a.start_date()),
                        Duration.ofSeconds(a.elapsed_time())),
                a.description())
        );
    }

    public void updateDescription(Long activityId, String description) throws IOException {
        stravaApi.updateActivity(String.valueOf(activityId), new ActivityUpdate(description)).execute();
    }
}

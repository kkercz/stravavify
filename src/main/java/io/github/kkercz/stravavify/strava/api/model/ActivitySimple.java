package io.github.kkercz.stravavify.strava.api.model;

// basic: https://developers.strava.com/docs/reference/#api-Activities-getLoggedInAthleteActivities
// detailed: https://developers.strava.com/docs/reference/#api-Activities-getActivityById
public record ActivitySimple(
        Long id,
        String start_date,
        String start_date_local,
        String timezone,
        String utc_offset,
        Long elapsed_time
) {}

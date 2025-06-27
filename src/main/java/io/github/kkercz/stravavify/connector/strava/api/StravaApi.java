package io.github.kkercz.stravavify.connector.strava.api;

import io.github.kkercz.stravavify.connector.strava.api.model.ActivityDetailed;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivitySimple;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivityUpdate;
import io.github.kkercz.stravavify.connector.strava.api.model.RefreshTokenRequest;
import io.github.kkercz.stravavify.connector.strava.api.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface StravaApi {

    String STRAVA_API_BASE_URL = "https://www.strava.com/api/v3/";

    @GET("athlete/activities")
    Call<List<ActivitySimple>> getLatestActivities(@Query("after") long afterEpoch);

    @GET("activities/{id}")
    Call<ActivityDetailed> getActivity(@Path("id") String activityId);

    @PUT("activities/{id}")
    Call<ActivityDetailed> updateActivity(@Path("id") String activityId, @Body ActivityUpdate activity);

    static StravaApi create(String token) {
        return RetrofitUtil.createApi(StravaApi.class, STRAVA_API_BASE_URL, new AuthInterceptor(token));
    }

    interface Authentication {
        @POST("oauth/token")
        Call<TokenResponse> refreshToken(@Body RefreshTokenRequest request);

        static StravaApi.Authentication create() {
            return RetrofitUtil.createApi(StravaApi.Authentication.class, STRAVA_API_BASE_URL);
        }
    }
}

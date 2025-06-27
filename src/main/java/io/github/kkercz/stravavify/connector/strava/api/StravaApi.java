package io.github.kkercz.stravavify.connector.strava.api;

import com.squareup.moshi.Moshi;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivityDetailed;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivitySimple;
import io.github.kkercz.stravavify.connector.strava.api.model.ActivityUpdate;
import io.github.kkercz.stravavify.connector.strava.api.model.RefreshTokenRequest;
import io.github.kkercz.stravavify.connector.strava.api.model.TokenResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface StravaApi {
    @POST("oauth/token")
    Call<TokenResponse> refreshToken(@Body RefreshTokenRequest request);

    @GET("athlete/activities?after={afterEpoch}")
    Call<List<ActivitySimple>> getLatestActivities(@Header("Authorization") String authHeader, @Query("afterEpoch") int afterEpoch);

    @GET("activities/{id}")
    Call<ActivityDetailed> getActivity(@Header("Authorization") String authHeader, @Path("id") String activityId);

    @PUT("activities/{id}")
    Call<ActivityDetailed> updateActivity(@Header("Authorization") String authHeader, @Path("id") String activityId, @Body ActivityUpdate activity);

    static StravaApi create() {
        Moshi moshi = new Moshi.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.strava.com/api/v3/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        return retrofit.create(StravaApi.class);
    }
}

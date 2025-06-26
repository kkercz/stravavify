package io.github.kkercz.stravavify.strava.api;

import com.squareup.moshi.Moshi;
import io.github.kkercz.stravavify.strava.api.model.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.*;

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

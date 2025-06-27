package io.github.kkercz.stravavify.connector.strava.api;

import com.squareup.moshi.Moshi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitUtil {

    public static <T> T createApi(Class<T> type, String baseUrl) {
        return createApi(type, baseUrl, null);
    }

    public static <T> T createApi(Class<T> type, String baseUrl, @Nullable Interceptor interceptor) {
        Moshi moshi = new Moshi.Builder().build();

        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi));

        if (interceptor != null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            retrofit.client(okHttpClient);
        }

        return retrofit.build().create(type);
    }
}

package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceUserProvider {

    private static final RestServiceUserProvider INSTANCE = new RestServiceUserProvider();
    private UserRestService service;

    private RestServiceUserProvider() {
    }

    public static RestServiceUserProvider newInstance() {
        return INSTANCE;
    }

    public synchronized final UserRestService getRestService() {
        if (service == null) {
            service = createRestService();
        }
        return service;
    }

    private UserRestService createRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserRestService.BASE_URL)
                .client(provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(UserRestService.class);
    }

    private OkHttpClient provideClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }
}

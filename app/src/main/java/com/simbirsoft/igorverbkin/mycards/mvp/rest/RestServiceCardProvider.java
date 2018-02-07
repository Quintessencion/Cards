package com.simbirsoft.igorverbkin.mycards.mvp.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestServiceCardProvider {

    private static final RestServiceCardProvider INSTANCE = new RestServiceCardProvider();
    private CardRestService service;

    private RestServiceCardProvider() {
    }

    public static RestServiceCardProvider newInstance() {
        return INSTANCE;
    }

    public synchronized final CardRestService getRestService() {
        if (service == null) {
            service = createRestService();
        }
        return service;
    }

    private CardRestService createRestService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CardRestService.BASE_URL)
                .client(provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(CardRestService.class);
    }

    private OkHttpClient provideClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }
}

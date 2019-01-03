package com.example.karim.anta5a.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSystem {
    private static final String BASE_URL = "http://alfarouqomar-001-site1.itempurl.com/System/";
    private static RetrofitSystem system;
    private static Retrofit retrofit;

    private RetrofitSystem() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitSystem getInstance() {
        if (system == null)
            system = new RetrofitSystem();
        return system;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}

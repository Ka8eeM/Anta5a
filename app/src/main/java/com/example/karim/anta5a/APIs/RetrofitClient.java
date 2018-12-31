package com.example.karim.anta5a.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://alfarouqomar-001-site1.itempurl.com/Customer/";
    private static RetrofitClient client;
    private static Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (client == null)
            client = new RetrofitClient();
        return client;
    }
    public Api getApi()
    {
        return  retrofit.create(Api.class);
    }
}

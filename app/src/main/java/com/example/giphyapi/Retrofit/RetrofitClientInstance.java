package com.example.giphyapi.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    // Declare and Init our base url
    public static final String base_url = "http://shibe.online/api/";

    // Declare Retrofit object
    private static Retrofit retrofit;

    // Create a private constructor
    private RetrofitClientInstance(){}

    // Create public static method to get instance of the retrofit object
    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

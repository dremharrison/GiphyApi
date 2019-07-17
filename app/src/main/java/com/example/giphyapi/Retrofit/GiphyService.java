package com.example.giphyapi.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {
    @GET("shibes")
    Call<List<String>> loadGiphy(@Query("count") int count);
}

package com.example.connie.moviesguide.model.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiClient {

    public static final String BASEURL = "https://api.themoviedb.org/";
    public static Retrofit retrofit = null;

    public static Retrofit getMovieApiClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

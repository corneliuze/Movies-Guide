package com.example.connie.moviesguide.model.service;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiInterface {

    //discover movie
    @GET("3/discover/movie?api_key=530409b67fad675e7ee11b7fb566d42e&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false")
    retrofit2.Call<ApiResponse> getMovieDiscover();

    // discover series
    @GET("3/discover/tv?api_key=530409b67fad675e7ee11b7fb566d42e&language=en-US&sort_by=popularity.desc&page=1&timezone=America%2FNew_York&include_null_first_air_dates=false")
    retrofit2.Call <ApiResponse> getSeriesDiscover();

    //search movie
    @GET("https://api.themoviedb.org/3/search/movie?api_key=530409b67fad675e7ee11b7fb566d42e&language=en-US&page=1&include_adult=false")
    retrofit2.Call <ApiResponse> searchMovies(@Query("query") String movieTitle);

    //search tv
    @GET("3/search/tv?api_key=530409b67fad675e7ee11b7fb566d42e&language=en-US")
    retrofit2.Call <ApiResponse> searchTv(@Query("query") String tvSeries);


}

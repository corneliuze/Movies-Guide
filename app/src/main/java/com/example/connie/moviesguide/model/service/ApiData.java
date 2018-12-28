package com.example.connie.moviesguide.model.service;

import android.util.Log;

import com.example.connie.moviesguide.View.Activities.MainActivity;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class ApiData {
    private MovieApiClient movieApiClient;
    private MovieApiInterface movieApiInterface;
    private MovieModel movieRepo;
    private MovieViewModel movieViewModel;
    private String  title;
    public static final String TAG = MainActivity.class.getSimpleName();


    public ApiData(MovieApiClient movieApiClient, MovieApiInterface movieApiInterface, MovieModel movieRepo) {
        this.movieApiClient = movieApiClient;
        this.movieApiInterface = movieApiInterface;
        this.movieRepo = movieRepo;

    }


    public void getMovieApiData() {
        Call<MovieModel> call = movieApiInterface.getMovieDiscover();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieRepo = response.body();

                Log.e(TAG, "api data is here");
                String title = movieRepo.getTitle();
                String imagePath = movieRepo.getPosterPath();
                String detail = movieRepo.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);

                }



            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });

    }

    public void getSeriesApiData(){
        Call<MovieModel> call = movieApiInterface.getSeriesDiscover();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieRepo = response.body();
                String title = movieRepo.getTitle();
                String imagePath = movieRepo.getPosterPath();
                String detail = movieRepo.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);


                }


            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    public void getSeriesApiSearch(){
        Call<MovieModel> call = movieApiInterface.searchTv("query");
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                movieRepo = response.body();
                String title = movieRepo.getTitle();
                String imagePath = movieRepo.getPosterPath();
                String detail = movieRepo.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);

                }



            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
    public void getMovieApiSearch(){

        Call<MovieModel> call = movieApiInterface.searchMovies("query");
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call <MovieModel> call, Response<MovieModel> response) {
                movieRepo = response.body();
                String title = movieRepo.getTitle();
                String imagePath = movieRepo.getPosterPath();
                String detail = movieRepo.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);



                }


            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

}

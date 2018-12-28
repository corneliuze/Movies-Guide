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
    private Result movieRepo;
    private MovieViewModel movieViewModel;
    private List<Result_> movieData;

    public static final String TAG = MainActivity.class.getSimpleName();


    public ApiData(MovieApiClient movieApiClient, MovieApiInterface movieApiInterface, Result movieRepo) {
        this.movieApiClient = movieApiClient;
        this.movieApiInterface = movieApiInterface;
        this.movieRepo = movieRepo;

    }


    public void getMovieApiData() {
        Call<Result> call = movieApiInterface.getMovieDiscover();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                movieRepo = response.body();

                for (int i = 0; i< movieData.size(); i++) {


                    Log.e(TAG, "api data is here");
                    String title = movieRepo.getResults().get(i).getTitle();
                    String imagePath = movieRepo.getResults().get(i).getPosterPath();
                    String detail = movieRepo.getResults().get(i).getOverview();

                    Movie movie = new Movie(title, imagePath, detail);

                    movieViewModel.insertMovie(movie);
                }
                }



            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }

    public void getSeriesApiData(){
        Call<Result> call = movieApiInterface.getSeriesDiscover();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                movieRepo = response.body();
                for (int i = 0; i< movieData.size(); i++) {
                String title = movieRepo.getResults().get(i).getTitle();
                String imagePath = movieRepo.getResults().get(i).getPosterPath();
                String detail = movieRepo.getResults().get(i).getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);

                }}


            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public void getSeriesApiSearch(){
        Call<Result> call = movieApiInterface.searchTv("query");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                movieRepo = response.body();
                for (int i = 0; i < movieData.size(); i++) {
                    String title = movieRepo.getResults().get(i).getTitle();
                    String imagePath = movieRepo.getResults().get(i).getPosterPath();
                    String detail = movieRepo.getResults().get(i).getOverview();

                    Movie movie = new Movie(title, imagePath, detail);
                    movieViewModel.insertMovie(movie);

                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    public void getMovieApiSearch(){

        Call<Result> call = movieApiInterface.searchMovies("query");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call <Result> call, Response<Result> response) {
                movieRepo = response.body();
                for (int i = 0; i< movieData.size(); i++) {
                String title = movieRepo.getResults().get(i).getTitle();
                String imagePath = movieRepo.getResults().get(i).getPosterPath();
                String detail = movieRepo.getResults().get(i).getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                movieViewModel.insertMovie(movie);




                }}


            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

}

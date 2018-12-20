package com.example.connie.moviesguide.model.service;

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
    private List<MovieModel> movieRepo;
    private MovieViewModel movieViewModel;
    private String  title;


    public ApiData(MovieApiClient movieApiClient, MovieApiInterface movieApiInterface, List<MovieModel> movieRepo) {
        this.movieApiClient = movieApiClient;
        this.movieApiInterface = movieApiInterface;
        this.movieRepo = movieRepo;

    }


    public void getMovieApiData() {
        Call<List<MovieModel>> call = movieApiInterface.getMovieDiscover();
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                movieRepo = response.body();
                for (int w = 0; w < movieRepo.size(); w++) {
                    String title = movieRepo.get(w).getTitle();
                    String imagePath = movieRepo.get(w).getPosterPath();
                    String detail = movieRepo.get(w).getOverview();

                    Movie movie = new Movie(title, imagePath,  detail);

                    movieViewModel.insertMovie(movie);
                }
            }


            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

            }
        });

    }

    public void getSeriesApiData(){
        Call<List<MovieModel>> call = movieApiInterface.getSeriesDiscover();
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                movieRepo = response.body();
                for (int i = 0; i < movieRepo.size(); i++){
                    String title = movieRepo.get(i).getTitle();
                    String imagePath = movieRepo.get(i).getPosterPath();
                    String detail = movieRepo.get(i).getOverview();

                    Movie movie = new Movie(title, imagePath, detail);
                    movieViewModel.insertMovie(movie);
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

            }
        });
    }

    public void getSeriesApiSearch(){
        Call<List<MovieModel>> call = movieApiInterface.searchTv("query");
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                movieRepo = response.body();
                for (int i = 0; i< movieRepo.size(); i++){
                    String title = movieRepo.get(i).getTitle();
                    String imagePath = movieRepo.get(i).getPosterPath();
                    String detail = movieRepo.get(i).getOverview();

                    Movie movie = new Movie(title, imagePath, detail);
                    movieViewModel.insertMovie(movie);
                }

            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

            }
        });
    }
    public void getMovieApiSearch(){

        Call<List<MovieModel>> call = movieApiInterface.searchMovies("query");
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                movieRepo = response.body();
                for (int i = 0; i < movieRepo.size(); i++ ){
                    String title = movieRepo.get(i).getTitle();
                    String imagePath = movieRepo.get(i).getPosterPath();
                    String detail = movieRepo.get(i).getOverview();

                    Movie movie = new Movie(title, imagePath,  detail);
                    movieViewModel.insertMovie(movie);

                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

            }
        });
    }

}

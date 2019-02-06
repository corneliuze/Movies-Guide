package com.example.connie.moviesguide.model.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.connie.moviesguide.View.Activities.MainActivity;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieApiViewModel extends ViewModel {
    private MovieApiInterface movieApiInterface;
    private MovieViewModel movieViewModel;

    public static final String TAG = MainActivity.class.getSimpleName();

    final MutableLiveData<List<Movie>> results = new MutableLiveData<>();


    private Callback<ApiResponse> searchResponseCallback = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            List<Result> allResults = response.body().getResults();
//            Log.i("APIViewModel", "data received is: " + new Gson().toJson(allResults));
            List<Movie> allMovies = new ArrayList<>();
            for (Result result : allResults) {
                String title = result.getTitle();
                String imagePath = result.getPosterPath();
                String detail = result.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                allMovies.add(movie);
            }
            results.postValue(allMovies);
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            results.postValue(null);
        }
    };


    private Callback<ApiResponse> apiResponseCallback = new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            List<Result> allResults = response.body().getResults();
//            Log.i("APIViewModel", "data received is: " + new Gson().toJson(allResults));
            List<Movie> allMovies = new ArrayList<>();
            for (Result result : allResults) {
                String title = result.getTitle();
                String imagePath = result.getPosterPath();
                String detail = result.getOverview();

                Movie movie = new Movie(title, imagePath, detail);
                allMovies.add(movie);

                movieViewModel.insertMovie(movie);
            }
            results.postValue(allMovies);
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            results.postValue(null);
        }
    };

    public void init(Fragment fragment) {
        movieViewModel = ViewModelProviders.of(fragment).get(MovieViewModel.class);
        // initializing the viewmodel for the database
        Retrofit retrofit = MovieApiClient.getMovieApiClient();
        //getting the instance of retrofit
        movieApiInterface = retrofit.create(MovieApiInterface.class);
        //intializing the interface
    }

    public LiveData<List<Movie>> getMovieApiData() {
        Call<ApiResponse> call = movieApiInterface.getMovieDiscover();
        call.enqueue(apiResponseCallback);
        return results;
    }

    public LiveData<List<Movie>> getSeriesApiData() {
        Call<ApiResponse> call = movieApiInterface.getSeriesDiscover();
        call.enqueue(apiResponseCallback);
        return results;
    }

    public LiveData<List<Movie>> getSeriesApiSearch(String queryText) {
        Call<ApiResponse> call = movieApiInterface.searchTv(queryText);
        call.enqueue(searchResponseCallback);
        return results;
    }

    public LiveData<List<Movie>> getMovieApiSearch(String queryText) {
        Call<ApiResponse> call = movieApiInterface.searchMovies(queryText);
        call.enqueue(searchResponseCallback);
        return results;

    }


}

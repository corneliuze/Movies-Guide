package com.example.connie.moviesguide.model.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {
    private MovieDatabase movieDatabase;

    public MovieRepository(Application application){
        movieDatabase = MovieDatabase.getDatabase(application);
        }

    public LiveData<List<Movie>> getAllMovie() {
        return movieDatabase.movieDao().getAllMovies();
    }
    public LiveData<Movie> getMovieById(String id){
        return movieDatabase.movieDao().getMovieById(id);
    }

    public void deleteMovie(Movie movie){
        movieDatabase.movieDao().deleteMovie(movie);
    }
    public void insertMovie(Movie movie){
        movieDatabase.movieDao().insertMovie(movie);
    }

    public void deleteAllMovies(){
        movieDatabase.movieDao().deleteAllMovie();
    }
}

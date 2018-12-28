package com.example.connie.moviesguide.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.data.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel{
        private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(this.getApplication());
    }

    public LiveData<List<Movie>> getAllMovie(){
        return repository.getAllMovie();
    }
    public LiveData<Movie> getMovieById(String id){
        return repository.getMovieById(id);
    }
    public void insertMovie(Movie movie){
        MovieViewModel.insertTask insertTask = new MovieViewModel.insertTask();
        insertTask.execute(movie);
    }
    public void deleteMovie(Movie movie){
        MovieViewModel.deleteTask deleteTask = new MovieViewModel.deleteTask();
        deleteTask.execute(movie);
    }

    public void deleteAllMovie(){
        MovieViewModel.deleteAllTasks deleteAllTasks = new MovieViewModel.deleteAllTasks();
        deleteAllTasks.execute();
    }

    public class insertTask extends AsyncTask<Movie, Void, Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
                repository.insertMovie(movies[0]);
            return null;
        }
        }

        public class deleteTask extends AsyncTask<Movie, Void, Void>{

            @Override
            protected Void doInBackground(Movie... movies) {
                repository.deleteMovie(movies[0]);
                return null;
            }
        }

        public class deleteAllTasks extends AsyncTask<Movie, Void, Void>{
        @Override
            protected Void doInBackground(Movie... movies) {
                repository.deleteAllMovies();
                return null;
            }
        }

}

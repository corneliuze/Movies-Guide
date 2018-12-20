package com.example.connie.moviesguide.model.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT *FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT *FROM MOVIE WHERE mId =:id")
    LiveData<Movie> getMovieById(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMovie(Movie movie);

    @Delete
     void deleteMovie(Movie movie);

    @Query("DELETE from Movie")
     void deleteAllMovie();
}

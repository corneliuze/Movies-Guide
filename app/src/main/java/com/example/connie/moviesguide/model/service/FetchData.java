package com.example.connie.moviesguide.model.service;

import com.example.connie.moviesguide.model.data.MovieRepository;

public class FetchData {
    private MovieRepository movieRepository;

    public FetchData(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public void showDataForDiscover(){
        movieRepository.getAllMovie();
    }

    public void showDataForSearch(){
        movieRepository.getMovieById("query");
    }


}

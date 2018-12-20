package com.example.connie.moviesguide.View.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Adapters.MovieAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.data.MovieRepository;
import com.example.connie.moviesguide.model.service.ApiData;
import com.example.connie.moviesguide.model.service.MovieApiClient;
import com.example.connie.moviesguide.model.service.MovieApiInterface;
import com.example.connie.moviesguide.model.service.MovieModel;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieAdapter movieAdapter;
    private MovieApiInterface movieApiInterface;
    private  List<MovieModel> movieRepo;
    private MovieViewModel movieViewModel;
    private ApiData movieApiData;
    private MovieApiClient movieApiClient;
    private MovieRepository movieRepository;
    private Movie movie;




    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies2, container, false);
        recyclerView = view.findViewById(R.id.movie_detail_recyclerview);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(context, (List<Movie>) movie);
        recyclerView.setAdapter(movieAdapter);
        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        movieApiData = new ApiData(movieApiClient, movieApiInterface, movieRepo);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        android.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (movie != null){
                    movieRepository.deleteAllMovies();
                    movieApiData.getMovieApiSearch();
                    movieViewModel.getMovieById(s);
                }else{
                    movieApiData.getMovieApiSearch();
                    movieViewModel.getMovieById(s);
                    }
                    return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        displayData();

        // Inflate the layout for this fragment
        return view;

    }

    public void displayData(){
        if (movie != null){
            movieViewModel.deleteAllMovie();
            movieApiData.getMovieApiData();
            setView();
            getAllMovie();

        }else{
            movieApiData.getMovieApiData();
            setView();
            getAllMovie();

        }
    }

    public void getAllMovie(){
        movieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setData(movies);
            }
        });
    }
    public void setView(){
        movieAdapter = new MovieAdapter(context, (List<Movie>) movie);
        recyclerView.setAdapter(movieAdapter);
    }





}

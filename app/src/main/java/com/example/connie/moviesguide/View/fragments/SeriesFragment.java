package com.example.connie.moviesguide.View.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Activities.DetailSeries;
import com.example.connie.moviesguide.View.Adapters.MovieListAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.data.MovieRepository;
import com.example.connie.moviesguide.model.service.ApiData;
import com.example.connie.moviesguide.model.service.MovieApiClient;
import com.example.connie.moviesguide.model.service.MovieApiInterface;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment implements MovieListAdapter.OnClickListener {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ApiData seriesApiData;
    private MovieListAdapter movieListAdapter;
    private Context context;
    private MovieApiInterface movieApiInterface;
    private MovieModel movieRepo;
    private MovieApiClient movieApiClient;
    private Movie movie;
    private MovieRepository movieRepository;
    private MovieViewModel movieViewModel;
    MovieListAdapter.OnClickListener onClickListener;

    public SeriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series2, container, false);
        recyclerView = view.findViewById(R.id.series_grid_recyclerview);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) movie);
        recyclerView.setAdapter(movieListAdapter);
        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        seriesApiData = new ApiData(movieApiClient, movieApiInterface, movieRepo);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        android.widget.SearchView searchView = view.findViewById(R.id.searchView_series);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                if (movie != null){
                    movieRepository.deleteAllMovies();
                    seriesApiData.getSeriesApiData();
                    movieViewModel.getMovieById(s);

                }else{
                    seriesApiData.getSeriesApiSearch();
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
            seriesApiData.getSeriesApiData();
            setView();
            getAllSeries();

        }
        seriesApiData.getSeriesApiData();
        setView();
        getAllSeries();
        }

    public void getAllSeries(){
        movieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieListAdapter.setData(movies);
            }
        });
    }
    public void setView(){
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) movie);
        recyclerView.setAdapter(movieListAdapter);
    }


    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailSeries.class);
        startActivity(intent);

    }
}

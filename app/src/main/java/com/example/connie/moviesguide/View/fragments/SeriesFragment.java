package com.example.connie.moviesguide.View.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Activities.DetailSeries;
import com.example.connie.moviesguide.View.Adapters.MovieListAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.data.MovieRepository;
import com.example.connie.moviesguide.model.service.MovieApiViewModel;
import com.example.connie.moviesguide.model.service.MovieApiClient;
import com.example.connie.moviesguide.model.service.MovieApiInterface;
import com.example.connie.moviesguide.model.service.ApiResponse;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment implements MovieListAdapter.OnClickListener {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MovieApiViewModel seriesMovieApiViewModel;
    private MovieListAdapter movieListAdapter;
    private Context context;
    private MovieApiInterface movieApiInterface;
    private ApiResponse ApiResponse;
    private MovieApiClient movieApiClient;
    private ArrayList<Movie> allSeries = new ArrayList<>();
    private MovieRepository movieRepository;
    private MovieViewModel movieViewModel;
    MovieListAdapter.OnClickListener onClickListener;
    private boolean isConnected;
    private String queryText;
    private MoviesFragment moviesFragment;

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
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) allSeries, this);
        recyclerView.setAdapter(movieListAdapter);
        context = getActivity().getApplicationContext();
        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        seriesMovieApiViewModel = ViewModelProviders.of(this).get(MovieApiViewModel.class);
        seriesMovieApiViewModel.init(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        setView();
        displayData();
        android.widget.SearchView searchView = view.findViewById(R.id.searchView_series);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!isConnected()) {
                    Toast.makeText(context, "switch on your internet connection", Toast.LENGTH_SHORT).show();

                } else {

                    seriesMovieApiViewModel.getSeriesApiSearch(s);

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        // the display is for when the search view is not in use
        // Inflate the layout for this fragment
        return view;

    }

    public String getQueryText() {
        return queryText;
    }

    public void displayData() {
        Observer<List<Movie>> apiObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies == null) {
                    getAllSeries();
                } else {
                    allSeries = (ArrayList<Movie>) movies;
                    movieListAdapter.setData(allSeries);

                }
            }
        };
        if (!isConnected()) {
            getAllSeries();
        } else {
            seriesMovieApiViewModel.getSeriesApiData().observe(SeriesFragment.this, apiObserver);

        }
    }


    public void getAllSeries() {
        movieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieListAdapter.setData(movies);
            }
        });
    }

    public void setView() {
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) allSeries, this);
        recyclerView.setAdapter(movieListAdapter);
    }


    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailSeries.class);
        startActivity(intent);

    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet != null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }
}

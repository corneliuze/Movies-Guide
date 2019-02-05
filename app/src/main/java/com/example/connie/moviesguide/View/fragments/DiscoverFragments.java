package com.example.connie.moviesguide.View.fragments;


import android.app.Activity;
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

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Activities.DetailsMovies;
import com.example.connie.moviesguide.View.Activities.MainActivity;
import com.example.connie.moviesguide.View.Adapters.MovieListAdapter;
import com.example.connie.moviesguide.model.data.Movie;
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
public class DiscoverFragments extends Fragment implements MovieListAdapter.OnClickListener {

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieApiViewModel movieMovieApiViewModel;
    private MovieApiClient movieApiClient;
    private MovieApiInterface movieApiInterface;
    private ArrayList<Movie> allMovies = new ArrayList<>();
    Spinner spinner;
    private MovieViewModel movieViewModel;
    private MovieListAdapter movieListAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    MovieListAdapter.OnClickListener onClickListener;
    private boolean isConnected;


    public DiscoverFragments() {
        // Required empty public constructor
    }

    /**
     this fragment shows the latest movies and tv shows, but it shows movie by default and there is a spinner that gives you opportunity
     to choose between tv shows and series
      **/


// the Observer class observes the list of movies the api is returning and adding to the arraylist of all movies from the movieapiviewmodel class
    Observer<List<Movie>> apiObserver = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies == null) {
                getAllMovie();
            } else {
                allMovies = (ArrayList<Movie>) movies;
//                Log.i(TAG, "Observed data size is:" + movies.size());
//                Log.i(TAG, "Observed data is:" + new Gson().toJson(movies));
                movieListAdapter.setData(allMovies);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_fragments, container, false);

        context = getActivity().getApplicationContext();
        recyclerView = view.findViewById(R.id.movie_discover_recycler_view);
        // i'm setting he adapter somewhere down in the setView method
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        movieMovieApiViewModel = ViewModelProviders.of(this).get(MovieApiViewModel.class);
        movieMovieApiViewModel.init(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        spinner = view.findViewById(R.id.options_spinner);
        setView();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    adapterView.getItemAtPosition(position);
                    if (!isConnected()) {
                        getAllMovie();

                    } else {
                        Log.e(TAG, "data is empty");
                        movieMovieApiViewModel.getMovieApiData()
                                .observe(DiscoverFragments.this, apiObserver);
                        Log.e(TAG , "heyyo");

                    }
                }

                else {
                    adapterView.getItemAtPosition(position);
                    if (!isConnected()) {
                        getAllMovie();


                    } else {
                        Log.e(TAG, "something is right here please");
                        movieMovieApiViewModel.getSeriesApiData().observe(DiscoverFragments.this, apiObserver);
                        }


                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (!isConnected) {
                    Log.i(TAG, "no internet connection");
                    getAllMovie();
                }else {
                    Log.i(TAG, "yes internet connection");
                    movieMovieApiViewModel.getMovieApiData().observe(DiscoverFragments.this, apiObserver);
                }
            }
        });

        return view;}

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsMovies.class);
        startActivity(intent);
    }

    public void getAllMovie() {
        movieViewModel.getAllMovie().observe(DiscoverFragments.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                allMovies = (ArrayList<Movie>) movies;
                movieListAdapter.setData(allMovies);
            }
        });

    }

    public void setView() {
        movieListAdapter = new MovieListAdapter(context, onClickListener, allMovies, this);
        recyclerView.setAdapter(movieListAdapter);
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet != null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }


}

package com.example.connie.moviesguide.View.fragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Activities.DetailsMovies;
import com.example.connie.moviesguide.View.Activities.MainActivity;
import com.example.connie.moviesguide.View.Adapters.MovieListAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.service.MovieApiViewModel;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MovieListAdapter.OnClickListener {


    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieListAdapter movieListAdapter;
    Observer<List<Movie>> apiObserver = new Observer<List<Movie>>() {
        @Override
        public void onChanged(@Nullable List<Movie> movies) {
            if (movies == null) {
                getAllMovie();
            } else {
//                allMovies = movies;
                Log.i(TAG, "Observed data size is:" + movies.size());
                movieListAdapter.setData(movies);
            }
        }
    };
    private MovieViewModel movieViewModel;
    private MovieApiViewModel movieMovieApiViewModel;

    MovieListAdapter.OnClickListener onClickListener;
    private boolean isConnected;
    public static final String TAG = MainActivity.class.getSimpleName();


    public MoviesFragment() {
        // Required empty public constructor
    }

    private List<Movie> allMovies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies2, container, false);
        recyclerView = view.findViewById(R.id.movie_grid_recycler_view);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        context = getActivity().getApplicationContext();
        movieMovieApiViewModel = ViewModelProviders.of(this).get(MovieApiViewModel.class);
        movieMovieApiViewModel.init(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        android.widget.SearchView searchView = view.findViewById(R.id.searchView_movie);
        displayData();


        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!isConnected()) {
                    Toast.makeText(context, "Turn On Your Connection", Toast.LENGTH_SHORT).show();
                } else {

                    Log.i(TAG, "this is what you searched for :" + s);
                    movieMovieApiViewModel.getMovieApiSearch(s).observe(MoviesFragment.this, apiObserver);

                    Log.i(TAG, "htgwm" + allMovies);

//                    setView();
                    //movieMovieApiViewModel.getMovieApiSearch(s);

                }
                // since the user need to search, he does need internet connection so its just query and display
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        // Inflate the layout for this fragment
        return view;

    }

    public void displayData() {
        if (!isConnected()) {
            getAllMovie();
        } else {
            movieMovieApiViewModel.getMovieApiData().observe(MoviesFragment.this, apiObserver);
            setView();
        }

    }

    public void getAllMovie() {
        movieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieListAdapter.setData(movies);
            }
        });
    }

    public void setView() {
        movieListAdapter = new MovieListAdapter(context, onClickListener, allMovies, this);
        recyclerView.setAdapter(movieListAdapter);
    }


    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsMovies.class);
        startActivity(intent);

    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet != null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }
}

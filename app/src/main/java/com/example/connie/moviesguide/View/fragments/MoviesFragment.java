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
import com.example.connie.moviesguide.View.Activities.DetailsMovies;
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
public class MoviesFragment extends Fragment implements MovieListAdapter.OnClickListener {


    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieListAdapter movieListAdapter;
    private MovieApiInterface movieApiInterface;
    private ApiResponse movieRepo;
    private MovieViewModel movieViewModel;
    private MovieApiViewModel movieMovieApiViewModel;
    private MovieApiClient movieApiClient;
    private MovieRepository movieRepository;
    private ArrayList<Movie> allMovie = new ArrayList<>();
    MovieListAdapter.OnClickListener onClickListener;
    private  boolean isConnected;
    private  String queryText;
    private SeriesFragment seriesFragment;




    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies2, container, false);
        recyclerView = view.findViewById(R.id.movie_grid_recycler_view);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) allMovie, this);
        recyclerView.setAdapter(movieListAdapter);
        context= getActivity().getApplicationContext();
        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        movieMovieApiViewModel = ViewModelProviders.of(this).get(MovieApiViewModel.class);
        movieMovieApiViewModel.init(this);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        android.widget.SearchView searchView = view.findViewById(R.id.searchView_movie);
        setView();
        displayData();

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                 queryText = s;
                if (!isConnected()){
                    Toast.makeText(context, "Turn On Your Connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    movieMovieApiViewModel.getMovieApiSearch(s);
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

    public void displayData(){
        Observer<List<Movie>> apiObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies == null){
                    getAllMovie();
                }
               allMovie = (ArrayList<Movie>) movies;
                movieListAdapter.setData(allMovie);
            }
        };
        if (!isConnected()){
            getAllMovie();
        }else{

                movieMovieApiViewModel.getMovieApiData().observe(MoviesFragment.this, apiObserver);




        }

    }

    public void getAllMovie(){
        movieViewModel.getAllMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieListAdapter.setData(movies);
            }
        });
    }
    public void setView(){
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) allMovie, this);
        recyclerView.setAdapter(movieListAdapter);
    }


    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsMovies.class);
        startActivity(intent);

    }

    public  boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet!= null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }
}

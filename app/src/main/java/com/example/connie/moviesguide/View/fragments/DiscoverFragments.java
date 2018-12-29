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
import com.example.connie.moviesguide.model.data.MovieRepository;
import com.example.connie.moviesguide.model.service.ApiData;
import com.example.connie.moviesguide.model.service.MovieApiClient;
import com.example.connie.moviesguide.model.service.MovieApiInterface;
import com.example.connie.moviesguide.model.service.Result;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragments extends Fragment implements MovieListAdapter.OnClickListener{

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ApiData movieApiData;
    private MovieApiClient movieApiClient;
    private MovieApiInterface movieApiInterface;
    private Movie movie;
    private Result movieRepo;
    Spinner spinner;
    private MovieViewModel movieViewModel;
    private MovieListAdapter movieListAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();
    MovieListAdapter.OnClickListener onClickListener;
    private boolean isConnected;
    private  MoviesFragment moviesFragment;
    private SeriesFragment seriesFragment;


    public DiscoverFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_fragments, container, false);

        context = getActivity().getApplicationContext();
        recyclerView = view.findViewById(R.id.movie_discover_recycler_view);
        // i'm setting he adapter somewhere down in the setView metho
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        Log.e(TAG, "recycler view set, awaiting data");


        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        movieApiData = new ApiData(movieApiClient, movieApiInterface, movieRepo, moviesFragment, seriesFragment);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        spinner = view.findViewById(R.id.options_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.sort_by, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.e(TAG, "wait, while we load the data for you");
        return  view;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsMovies.class);
        startActivity(intent);
    }


    public  class  SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0){
                Log.e(TAG, "something is wrong here please");
                adapterView.getItemAtPosition(i);
                if (isConnected() == false){
                    setView();
                    getAllMovie();

                        }else{
                    if (movie != null){
                    movieViewModel.deleteAllMovie();
                    Log.e(TAG, "something is wrong, view model is not deleting");
                    movieApiData.getMovieApiData();
                    movieViewModel.insertMovie(movie);
                        setView();
                    getAllMovie();

                    Log.e(TAG, "something is wrong");
                    }else{
                    Log.e(TAG, "data is empty");
                    movieApiData.getMovieApiData();
                    Log.e(TAG, "api is got data");
                    movieViewModel.insertMovie(movie);
                    setView();
                    Log.e(TAG, "view set");
                    getAllMovie();
                    Log.e(TAG, "all done");

                    }
                    }}else {
                adapterView.getItemAtPosition(i);
                if (isConnected() == false){
                        setView();
                        getAllMovie();


                }else{
                    if (movie != null){
                    movieViewModel.deleteAllMovie();
                    movieApiData.getSeriesApiData();
                    movieViewModel.insertMovie(movie);
                    setView();
                    getAllMovie();


                }else{
                movieApiData.getSeriesApiData();
                movieViewModel.insertMovie(movie);
                setView();
                getAllMovie();



                }}}}

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {// then default should be movie
   if (isConnected() == false){

       //if there is no connection and the database is not empty, just display what is in the database

       setView();
       getAllMovie();



   }else{
       if (movie != null){
           movieViewModel.deleteAllMovie(); // delete what is in the database
           //then make the call
           movieApiData.getMovieApiData();
          //let the livedata observe in getAllMovie method
           movieViewModel.insertMovie(movie);// then insert from the api to the database
           setView();// setView method is the one setting the adapter
           getAllMovie();// this is observing and setting the data from the database


       }
   }

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
        movieListAdapter = new MovieListAdapter(context, onClickListener, (List<Movie>) movie);
        recyclerView.setAdapter(movieListAdapter);
    }

    public  boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInternet = connectivityManager.getActiveNetworkInfo();
        isConnected = activeInternet!= null && activeInternet.isConnectedOrConnecting();
        return isConnected;
    }

   


}

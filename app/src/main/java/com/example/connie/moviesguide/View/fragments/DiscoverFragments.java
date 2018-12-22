package com.example.connie.moviesguide.View.fragments;


import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Activities.DetailsMovies;
import com.example.connie.moviesguide.View.Adapters.MovieAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.model.data.MovieRepository;
import com.example.connie.moviesguide.model.service.ApiData;
import com.example.connie.moviesguide.model.service.MovieApiClient;
import com.example.connie.moviesguide.model.service.MovieApiInterface;
import com.example.connie.moviesguide.model.service.MovieModel;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragments extends Fragment implements MovieAdapter.OnClickListener{

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ApiData movieApiData;
    private MovieApiClient movieApiClient;
    private MovieApiInterface movieApiInterface;
    private Movie movie;
    private List<MovieModel> movieRepo;
    Spinner spinner;
    private MovieViewModel movieViewModel;
    private MovieRepository movieRepository;
    private MovieAdapter movieAdapter;

    MovieAdapter.OnClickListener onClickListener;


    public DiscoverFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_fragments, container, false);

        context = getActivity().getApplicationContext();
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView = view.findViewById(R.id.movie_detail_recyclerview);

        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(context, onClickListener, (List<Movie>) movie);
        recyclerView.setAdapter(movieAdapter);

        movieApiInterface = MovieApiClient.getMovieApiClient().create(MovieApiInterface.class);
        movieApiData = new ApiData(movieApiClient, movieApiInterface, movieRepo);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        spinner = view.findViewById(R.id.options_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.sort_by, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return  view;
    }

    @Override
    public void onClick() {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsMovies.class);
        startActivity(intent);
    }


    public  class  SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0){
                adapterView.getItemAtPosition(i);
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
                    }else {
                adapterView.getItemAtPosition(i);
                if (movie != null){
                    movieViewModel.deleteAllMovie();
                    movieApiData.getSeriesApiData();
                    setView();
                    getAllMovie();


                }else{
                movieApiData.getSeriesApiData();
                setView();
                getAllMovie();



                }}}

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {


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
        movieAdapter = new MovieAdapter(context, onClickListener, (List<Movie>) movie);
        recyclerView.setAdapter(movieAdapter);
    }



}

package com.example.connie.moviesguide.View.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.View.Adapters.MovieDetailAdapter;
import com.example.connie.moviesguide.model.data.Movie;
import com.example.connie.moviesguide.viewmodels.MovieViewModel;

import java.util.List;

public class DetailsMovies extends AppCompatActivity {
    ImageView movieImageView;

    Button videosButton;
    private MovieDetailAdapter movieDetailAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private List<Movie> movie;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movies);

        movieImageView = findViewById(R.id.movie_detail_imageview);
        videosButton = findViewById(R.id.tailer_textview);
        recyclerView = findViewById(R.id.movie_detail_recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        movieDetailAdapter = new MovieDetailAdapter(context, movie);
        recyclerView.setAdapter(movieDetailAdapter);

    }
}

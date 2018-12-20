package com.example.connie.moviesguide.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.connie.moviesguide.R;
import com.example.connie.moviesguide.model.data.Movie;

import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> movie;

    public MovieDetailAdapter(Context context, List<Movie> movie){
        this.context = context;
         this.movie = movie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item, viewGroup, false);
        return new MovieViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        String detailTextView = movie.get(i).getmDetails();
                movieViewHolder.detailTextView.setText(detailTextView);

    }

    @Override
    public int getItemCount() {
        if (movie == null){
            return  0;
        }else{
            return movie.size();
        }

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView detailTextView;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTextView = itemView.findViewById(R.id.detail_textview);
        }
    }
}

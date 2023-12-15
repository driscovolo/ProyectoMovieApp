package com.example.proyectomovieapp.adapters;


import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.activities.DetailActivity;
import com.example.proyectomovieapp.models.Movie;




import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie.ResultsDTO> favoriteMovies;
    private Context context;

    public MovieAdapter(List<Movie.ResultsDTO> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_slide, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie.ResultsDTO movie = favoriteMovies.get(position);

        String posterUrl = movie.getPosterPath();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context).load(posterUrl).apply(requestOptions).into(holder.posterView);
        holder.titleView.setText(movie.getTitle());
        holder.posterView.setOnClickListener(view ->{
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("idMovie", favoriteMovies.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView posterView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            posterView = itemView.findViewById(R.id.posterView);
        }
    }
}
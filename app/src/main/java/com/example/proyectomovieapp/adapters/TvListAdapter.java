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
import com.example.proyectomovieapp.models.Series;

import java.util.List;

public class TvListAdapter extends RecyclerView.Adapter<TvListAdapter.ViewHolder>{
    List<Series> items;
    Context context;

    public TvListAdapter(List<Series> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_slide,parent,false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String poster_path = items.get(position).getPosterPath();
        if (poster_path != null) {
            String posterUrl = "https://image.tmdb.org/t/p/w500" + poster_path;
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(16));
            Glide.with(context).load(posterUrl).apply(requestOptions).into(holder.posterView);
        }
        String title = items.get(position).getName();
        holder.titleView.setText(title);
        holder.posterView.setOnClickListener(view ->  {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("tvId", items.get(position).getId());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        ImageView posterView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            posterView = itemView.findViewById(R.id.posterView);
        }
    }
}

package com.example.proyectomovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.activities.DetailActivity;


import java.util.List;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {
    private List<String> posterUrls;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapters(List<String> posterUrls, ViewPager2 viewPager2, Context context) {
        this.posterUrls = posterUrls;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                 R.layout.slide_item_container, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String imageUrl = posterUrls.get(position);
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context).load(imageUrl).apply(requestOptions).into(holder.imageView);
        if (position == posterUrls.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return posterUrls.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            posterUrls.addAll(posterUrls);
            notifyDataSetChanged();
        }
    };
}


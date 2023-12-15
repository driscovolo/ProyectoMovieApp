package com.example.proyectomovieapp.adapters;

import android.content.Context;
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
import com.example.proyectomovieapp.models.Credit;


/*
*Esta clase es la encargada de mostrar los actores de la pelicula
 */
public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder>{
    Credit images;
    Context context;

    public ActorAdapter(Credit images) {
        this.images = images;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_slide,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Credit.CastDTO cast = images.getCast().get(position);

        if (cast != null) {
            String profile_path = cast.getProfilePath();
            String name = cast.getName();

            if (profile_path != null) {
                String profileUrl = "https://image.tmdb.org/t/p/w500" + profile_path;
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transform(new CenterCrop() , new RoundedCorners(1000));
                Glide.with(context)
                    .load(profileUrl)
                    .apply(requestOptions)
                    .into(holder.actorView);
            }
            if (name != null) {
                int spaceIndex = name.indexOf(' ');
                if (spaceIndex > 0) {
                    String firstName = name.substring(0, spaceIndex);
                    String lastName = name.substring(spaceIndex + 1);
                    holder.actorName.setText(firstName + "\n" + lastName);
                } else {
                    holder.actorName.setText(name);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return images.getCast().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView actorView;
        TextView actorName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            actorView= itemView.findViewById(R.id.actorView);
            actorName= itemView.findViewById(R.id.actorName);
        }
    }
}

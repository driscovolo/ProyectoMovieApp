package com.example.proyectomovieapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.proyectomovieapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/**
 * Clase que se encarga de mostrar el trailer de la pelicula seleccionada
 */
public class TrailerActivity extends AppCompatActivity{
    private YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        //Se obtiene el key del video de la pelicula seleccionada
        String key = getIntent().getStringExtra("key");
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        //Se carga el video
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            /**
             * Metodo que se encarga de cargar el video
             * @param youTubePlayer
             */
            @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.loadVideo(key,0);
                }
            });
    }

}
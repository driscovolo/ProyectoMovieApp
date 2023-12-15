package com.example.proyectomovieapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.adapters.FilmListAdapter;
import com.example.proyectomovieapp.adapters.SliderAdapters;
import com.example.proyectomovieapp.api.TmdbApi;
import com.example.proyectomovieapp.models.Movie;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fraqmento que muestra las peliculas mas populares, las que estan por llegar y un banner con las
 * peliculas mejor calificadas
 */
public class MovieFragment extends Fragment {

    private RecyclerView.Adapter  adapterUpcomingMovies, adapterPopularMovies;
    private RecyclerView  recyclerViewUpcomingMovies, recyclerViewPopularMovies;
    private ViewPager2 viewPager2;
    private ProgressBar progressBar, progressBar2, progressBar3;
    private HandlerThread sliderHandlerThread = new HandlerThread("sliderHandler");

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView(view);
        sendRequest();
        bannersSlider();
        return view;
    }

    /**
     * Metodo que envia la peticion a la API para obtener las peliculas por llegar y las mas populares
     * y las muestra en el fragmento.
     */
    private void sendRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        //Peliculas por llegar
        Call<Movie> call = tmdbApi.getUpcomingMovies("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar3.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                Movie movie = response.body();
                adapterUpcomingMovies = new FilmListAdapter(movie);
                recyclerViewUpcomingMovies.setAdapter(adapterUpcomingMovies);
                progressBar3.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                progressBar3.setVisibility(View.GONE);
                System.out.println(t.getMessage());
            }
        });
        //Peliculas populares
        Call<Movie> call2 = tmdbApi.getPopularMovies("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar2.setVisibility(View.VISIBLE);
        call2.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                Movie movie = response.body();
                adapterPopularMovies = new FilmListAdapter(movie);
                recyclerViewPopularMovies.setAdapter(adapterPopularMovies);
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }
    /**
     * Metodo que envia la peticion a la API para obtener las peliculas mejor calificadas y las muestra
     * en un banner.
     */
    private void bannersSlider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        Call<Movie> call = tmdbApi.getTopRatedMovies("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                Movie movie = response.body();
                List<String> posterUrls = new ArrayList<>();
                for(int i = 0; i < movie.getResults().size(); i++){
                    posterUrls.add("https://image.tmdb.org/t/p/w500" + movie.getResults().get(i).getBackdropPath());
                }
                progressBar.setVisibility(View.GONE);

                // Crear y configurar el adaptador con las URL de los posters
                SliderAdapters sliderAdapter = new SliderAdapters(posterUrls, viewPager2, requireContext());
                viewPager2.setAdapter(sliderAdapter);
                viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer((new MarginPageTransformer(40)));
                compositePageTransformer.addTransformer(((page, position) -> {
                    //Escala los posters de los lados
                    float r = 1 - Math.abs(position);
                    page.setScaleY(0.85f + r * 0.15f);
                }));
                viewPager2.setPageTransformer(compositePageTransformer);
                viewPager2.setCurrentItem(1);
                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
                    /**
                     * Metodo que se ejecuta cuando se cambia de pagina en el banner
                     * @param position
                     */
                    @Override
                    public void onPageSelected(int position){
                        super.onPageSelected(position);
                        Handler sliderHandler = new Handler(sliderHandlerThread.getLooper());
                        sliderHandler.removeCallbacks(sliderRunnable);
                        sliderHandler.postDelayed(sliderRunnable, 4000);
                    }
                });
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    /**
     * Metodo que se ejecuta cuando se pausa la aplicacion
     * Se encarga de detener el hilo que se encarga de cambiar de pagina en el banner
     */
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    /**
     * Metodo que se ejecuta cuando se pausa la aplicacion
     */
    @Override
    public void onPause() {
        super.onPause();
        sliderHandlerThread.quitSafely();
    }

    /**
     * Metodo que se ejecuta cuando se reanuda la aplicacion
     */
    @Override
    public void onResume() {
        super.onResume();

        if (viewPager2 != null) {
            sliderHandlerThread = new HandlerThread("sliderHandler");
            sliderHandlerThread.start();
        }
    }

    /**
     * Metodo que inicializa los elementos de la vista
     * @param rootView
     */
    private void initView(View rootView) {
        if (rootView != null) {
            progressBar = rootView.findViewById(R.id.progressBar);
            progressBar2 = rootView.findViewById(R.id.progressBar2);
            progressBar3 = rootView.findViewById(R.id.progressBar3);
            viewPager2 = rootView.findViewById(R.id.viewPager);
            recyclerViewUpcomingMovies = rootView.findViewById(R.id.onAirView);
            recyclerViewUpcomingMovies.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewPopularMovies = rootView.findViewById(R.id.categoryView);
            recyclerViewPopularMovies.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
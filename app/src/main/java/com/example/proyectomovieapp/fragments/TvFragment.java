package com.example.proyectomovieapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.adapters.TvListAdapter;
import com.example.proyectomovieapp.api.TmdbApi;
import com.example.proyectomovieapp.models.Series;
import com.example.proyectomovieapp.models.SerieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento que muestra las series mas populares, las que estan en el aire y las mejor calificadas
 */
public class TvFragment extends Fragment {


    private RecyclerView.Adapter  adapterOnAirSeries, adapterPopularSeries, adapterTopRatedSeries;
    private RecyclerView  recyclerViewOnAirSeries, recyclerViewPopularSeries, recyclerViewTopRatedSeries;
    private ProgressBar progressBar, progressBar2, progressBar3;

    public TvFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        initView(view);
        sendRequest();
        return view;
    }

    /**
     * Metodo que envia la peticion a la API
     * para obtener las series en el aire, las mas populares y las mejor calificadas
     */
    private void sendRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
        //Series en el aire
        Call<SerieResponse> call = tmdbApi.getOnTheAirSeries("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar3.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SerieResponse>() {

            @Override
            public void onResponse(Call<SerieResponse> call, Response<SerieResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                SerieResponse serieResponse = response.body();
                List<Series> series = serieResponse.getResults();
                adapterOnAirSeries = new TvListAdapter(series);
                recyclerViewOnAirSeries.setAdapter(adapterOnAirSeries);
                progressBar3.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SerieResponse> call, Throwable t) {

            }
        });

        //Series populares
        Call<SerieResponse> call2 = tmdbApi.getPopularSeries("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar2.setVisibility(View.VISIBLE);
        call2.enqueue(new Callback<SerieResponse>() {
            @Override
            public void onResponse(Call<SerieResponse> call, Response<SerieResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                SerieResponse serieResponse = response.body();
                List<Series> series = serieResponse.getResults();
                adapterPopularSeries = new TvListAdapter(series);
                recyclerViewPopularSeries.setAdapter(adapterPopularSeries);
                progressBar2.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SerieResponse> call, Throwable t) {

            }
        });

        //Series mas valoradas
        Call<SerieResponse> call3 = tmdbApi.getTopRatedSeries("176c7dcafd1c399aa33ca01eb0d50c19");
        progressBar.setVisibility(View.VISIBLE);
        call3.enqueue(new Callback<SerieResponse>() {

            @Override
            public void onResponse(Call<SerieResponse> call, Response<SerieResponse> response) {
                if(!response.isSuccessful()){
                    System.out.println(response.code());
                    return;
                }
                SerieResponse serieResponse = response.body();
                List<Series> series = serieResponse.getResults();
                adapterTopRatedSeries = new TvListAdapter(series);
                recyclerViewTopRatedSeries.setAdapter(adapterTopRatedSeries);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SerieResponse> call, Throwable t) {

            }
        });
    }

    /**
     * Metodo que inicializa los componentes de la vista
     * @param rootView
     */
    private void initView(View rootView) {
        if (rootView != null) {
            progressBar = rootView.findViewById(R.id.progressBar);
            progressBar2 = rootView.findViewById(R.id.progressBar2);
            progressBar3 = rootView.findViewById(R.id.progressBar3);
            recyclerViewOnAirSeries = rootView.findViewById(R.id.onAirView);
            recyclerViewOnAirSeries.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewPopularSeries = rootView.findViewById(R.id.categoryView);
            recyclerViewPopularSeries.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopRatedSeries = rootView.findViewById(R.id.topRatedView);
            recyclerViewTopRatedSeries.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        }
    }
}
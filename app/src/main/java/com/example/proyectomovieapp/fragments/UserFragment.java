package com.example.proyectomovieapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.activities.LoginActivity;
import com.example.proyectomovieapp.adapters.FilmListAdapter;
import com.example.proyectomovieapp.adapters.MovieAdapter;
import com.example.proyectomovieapp.adapters.TvAdapter;
import com.example.proyectomovieapp.adapters.TvListAdapter;
import com.example.proyectomovieapp.models.Movie;
import com.example.proyectomovieapp.models.SerieResponse;
import com.example.proyectomovieapp.models.Series;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento que muestra el menu de usuario con las series y peliculas favoritas

 */
public class UserFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView logout,hello,text1,text3;
    Button loginButton;
    RecyclerView favoriteMoviesRecyclerView, favoriteSeriesRecyclerView;


    public UserFragment() {
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
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        iniView(view);
        if (mAuth.getCurrentUser() == null) {
            hello.setText("Sign up or log in to see your profile");
            logout.setVisibility(View.GONE);
            text1.setVisibility(View.GONE);
            text3.setVisibility(View.GONE);
           loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            });
        }
        else {
            loginButton.setVisibility(View.GONE);
            getUserData();
            hello.setText("Hello, " + mAuth.getCurrentUser().getEmail().substring(0, mAuth.getCurrentUser().getEmail().indexOf("@"))+"!");
        }

        return view;
    }

    /**
     * Metodo que obtiene los datos del usuario y los muestra en el menu
     *
     */
    private void getUserData() {
        String userId = mAuth.getCurrentUser().getUid();
        retrieveDataAndSetAdapter(userId, "favoriteMovies", favoriteMoviesRecyclerView, Movie.ResultsDTO.class, MovieAdapter.class);
        retrieveDataAndSetAdapter(userId, "favoriteSerie", favoriteSeriesRecyclerView, Series.class, TvAdapter.class);

    }

    /**
     * Metodo que obtiene los datos de la base de datos y los muestra en el menu del usuario
     * @param userId
     * @param child
     * @param recyclerView
     * @param modelClass
     * @param adapterClass
     * @param <T>
     * @param <A>
     */
    private <T, A extends RecyclerView.Adapter> void retrieveDataAndSetAdapter(String userId, String child, RecyclerView recyclerView, Class<T> modelClass, Class<A> adapterClass) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId)
                .child(child);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> items = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    T item = snapshot.getValue(modelClass);
                    items.add(item);
                }

                try {
                    A adapter = adapterClass.getConstructor(List.class).newInstance(items);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metodo que inicializa los componentes de la vista
     * @param rootView
     */
    private void iniView(View rootView) {
        text1= rootView.findViewById(R.id.text1);
        text3= rootView.findViewById(R.id.text3);
        hello= rootView.findViewById(R.id.hello);
        logout= rootView.findViewById(R.id.logout);
        loginButton= rootView.findViewById(R.id.loginButton);
        favoriteMoviesRecyclerView = rootView.findViewById(R.id.favorite_movies_recyclerview);
        favoriteSeriesRecyclerView = rootView.findViewById(R.id.favorite_tv_recyclerview);
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }


}
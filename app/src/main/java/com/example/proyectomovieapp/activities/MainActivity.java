package com.example.proyectomovieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.proyectomovieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Clase que representa la actividad principal de la aplicación.
 * Esta actividad contiene el menú de navegación y el fragmento de inicio.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navegacion();
    }

    /**
     * Método que se encarga de la navegación entre los fragmentos de la aplicación.
     */
    private void navegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }
}
package com.example.proyectomovieapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomovieapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * LoginActivity es una clase que se encarga de manejar el inicio de sesión de los usuarios.
 * Esta clase se encarga de validar que los campos de email y contraseña no estén vacíos y que
 * el usuario exista en la base de datos de Firebase.
 */

public class LoginActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText;
    Button loginButton;
    TextView registerButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressBar progressBar;

    /**
     * onStart es un método que se ejecuta cuando la actividad está visible para el usuario.
     * Este método se encarga de verificar si el usuario ya está logueado en la aplicación.
     */

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        logUser();
    }

    /**
     * logUser es un método que se encarga de validar que los campos de email y contraseña no estén
     * vacíos y que el usuario exista en la base de datos de Firebase.
     */
    private void logUser() {
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            // Validar que los campos no estén vacíos
            if (email.isEmpty()) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
            else {
                // Iniciar sesión con Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Toast.makeText(this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    /**
     * initView es un método que se encarga de inicializar los elementos de la vista.
     */
    private void initView() {
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progressBar);
    }
}
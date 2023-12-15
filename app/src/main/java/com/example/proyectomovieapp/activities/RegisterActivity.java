package com.example.proyectomovieapp.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomovieapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * RegisterActivity se encarga de registrar un usuario en la base de datos de Firebase
 */
public class RegisterActivity extends AppCompatActivity {
    EditText emailEditText,passwordEditText, confirmPasswordEditText;
    Button registerButton;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    ProgressBar progressBar;
    TextView backLogin;

    /**
     * onStart se encarga de verificar si el usuario ya esta logueado, si es asi, lo redirige a la MainActivity
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
        setContentView(R.layout.activity_register);
        initView();
        saveUser();
    }

    /**
     * saveUser se encarga de registrar un usuario en la base de datos de Firebase
     * Si el usuario ya esta registrado, lo redirige a la MainActivity
     */
    private void saveUser() {
        registerButton.setOnClickListener(v -> {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            //Valida si los campos estan vacios
            if (email.isEmpty()) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            //Valida si las contraseñas coinciden
            if (!password.equals(confirmPassword)) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
            //Valida si la contraseña tiene mas de 6 caracteres
            if (password.length() < 6) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            }

            else {
                //Registra el usuario en la base de datos de Firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Registro exitoso
                                Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(ProgressBar.GONE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(email)
                                        .build();
                                user.updateProfile(profileUpdates);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Si el registro falla, muestra un mensaje al usuario
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        //Redirige a la LoginActivity
        backLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }

    /**
     * initView se encarga de inicializar los elementos de la vista
     */
    private void initView() {
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        registerButton = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        backLogin = findViewById(R.id.BackLogin);
    }
}
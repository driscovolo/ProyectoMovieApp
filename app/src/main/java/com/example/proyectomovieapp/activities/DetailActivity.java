package com.example.proyectomovieapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.proyectomovieapp.R;
import com.example.proyectomovieapp.adapters.ActorAdapter;
import com.example.proyectomovieapp.adapters.CommentAdapter;
import com.example.proyectomovieapp.api.TmdbApi;
import com.example.proyectomovieapp.models.Comment;
import com.example.proyectomovieapp.models.Credit;
import com.example.proyectomovieapp.models.Movie;
import com.example.proyectomovieapp.models.Series;
import com.example.proyectomovieapp.models.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DetailActivity es una actividad que muestra los detalles de una película o una serie de televisión.
 * Obtiene datos de la API de TMDB y los muestra en la interfaz de usuario.
 * También permite a los usuarios publicar comentarios, calificar la película o la serie de televisión y añadir a una lista de favoritas las que quieran.
 */
public class DetailActivity extends AppCompatActivity {
private TextView textTitle,textDescription,ratingNumber,date,rate;
private int id;
private ImageView imageMovie,imageBackDrop;
private RecyclerView.Adapter adapterActorList;
private RecyclerView recyclerViewActor;
private FloatingActionButton trailerButton,favButton;
private RatingBar ratingBar;



    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        rating();
        //Obtener el id de la película o serie de televisión de la actividad anterior
        Intent intent = getIntent();
        if (intent.hasExtra("idMovie")) {
            id = intent.getIntExtra("idMovie", 0);
            sendMovieRequest();
            setupComments();
        } else if (intent.hasExtra("tvId")) {
            id = intent.getIntExtra("tvId", 0);
            sendTvRequest();
            setupComments();
        } else {
            finish();
        }
    }

    /**
     * Prepara el RecyclerView para mostrar los comentarios de Firebase
     * @author:Drisse Bouregaa
     * @param:
     */
    private void setupComments() {
        RecyclerView commentsRecyclerView = findViewById(R.id.comment_recycler_view);
        CommentAdapter commentAdapter = new CommentAdapter(new ArrayList<>());
        commentsRecyclerView.setAdapter(commentAdapter);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los comentarios de Firebase
        DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference()
                .child("comments")
                .child(String.valueOf(id));

        DatabaseReference movieOrSeriesCommentsRef = commentsRef.child(String.valueOf(id));

        movieOrSeriesCommentsRef.addValueEventListener(new ValueEventListener() {
            // Este método se ejecuta cada vez que se actualiza la base de datos
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Comment> comments = new ArrayList<>();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    comments.add(comment);
                }
                commentAdapter.updateComments(comments);
            }
            // Este método se ejecuta si hay un error al recuperar los comentarios
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DetailActivity", "Error retrieving comments: " + databaseError.getMessage());
            }
        });

        EditText commentEditText = findViewById(R.id.comment_edit_text);
        Button postCommentButton = findViewById(R.id.post_comment_button);
        postCommentButton.setOnClickListener(v -> {
            String commentText = commentEditText.getText().toString();
            if (!TextUtils.isEmpty(commentText)) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // Obtener el nombre de usuario del correo electrónico quitando la parte de despues del "@"
                    String userId = currentUser.getEmail().substring(0, currentUser.getEmail().indexOf("@"));
                    Comment comment = new Comment();
                    comment.setComment(commentText);
                    comment.setUser(userId);

                    // Guardar el comentario en Firebase
                    movieOrSeriesCommentsRef.push().setValue(comment)
                            // Este método se ejecuta si el comentario se guarda correctamente
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(DetailActivity.this, "Comment posted", Toast.LENGTH_SHORT).show();
                                commentEditText.setText("");
                            })
                            // Este método se ejecuta si hay un error al guardar el comentario
                            .addOnFailureListener(e -> {
                                Log.e("DetailActivity", "Error posting comment: " + e.getMessage());
                            });
                } else {
                    Toast.makeText(DetailActivity.this, "You need to be logged in to post a comment", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DetailActivity.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Este método configura la RatingBar para permitir a los usuarios calificar la película o la serie de televisión.
     */
    public void rating() {


        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference()
                            .child("ratings")
                            .child(String.valueOf(id))
                            .child(userId);

                    //Guarda la calificación en Firebase
                    ratingRef.setValue(rating)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(DetailActivity.this, "Rating saved", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                            });

                    //Calcula el promedio de todas las calificaciones
                    DatabaseReference allRatingsRef = FirebaseDatabase.getInstance().getReference()
                            .child("ratings")
                            .child(String.valueOf(id));

                    allRatingsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            double totalRating = 0.0;
                            int count = 0;

                            for (DataSnapshot userRatingSnapshot : dataSnapshot.getChildren()) {
                                Double rating = userRatingSnapshot.getValue(Double.class);
                                totalRating += rating;
                                count++;
                            }

                            double averageRating = totalRating / count;
                            ratingNumber.setText(String.format("%.1f", averageRating)+"/5");
                            ratingBar.setRating((float) averageRating);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("DetailActivity", "Error retrieving ratings: " + databaseError.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(DetailActivity.this, "You need to be logged in to rate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Este método envía una solicitud a la API de TMDB para obtener los detalles de una película.
     */
    private void sendMovieRequest() {
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TmdbApi tmdbApi = retrofit.create(TmdbApi.class);

            // Hacemos una llamada a la API de TMDB para obtener los detalles de la película
            Call<Movie.ResultsDTO> call = tmdbApi.getMovieDetails(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call.enqueue(new Callback<Movie.ResultsDTO>() {
                // Este método se ejecuta si la respuesta es correcta
                @Override
                public void onResponse(Call<Movie.ResultsDTO> call, retrofit2.Response<Movie.ResultsDTO> response) {
                    if (response.isSuccessful()) {
                        Movie.ResultsDTO movie = response.body();
                        handleMovieData(movie);
                        addMovie(movie);
                    } else {
                        System.out.println("Error");
                    }
                }

                @Override
                public void onFailure(Call<Movie.ResultsDTO> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            // Hacemos una llamada a la API de TMDB para obtener los créditos de la película
            Call<Credit> call2 = tmdbApi.getMovieCredits(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call2.enqueue(new Callback<Credit>() {
                @Override
                public void onResponse(Call<Credit> call, retrofit2.Response<Credit> response) {
                    if (response.isSuccessful()) {
                        Credit credit = response.body();
                        adapterActorList = new ActorAdapter(credit);
                        recyclerViewActor.setAdapter(adapterActorList);
                    } else {
                        System.out.println("Error");
                    }
                }

                @Override
                public void onFailure(Call<Credit> call, Throwable t) {
                    t.printStackTrace();

                }
            });

            // Hacemos una llamada a la API de TMDB para obtener los videos de la película en este caso el trailer
            Call<Video> call3 = tmdbApi.getMovieVideos(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call3.enqueue(new Callback<Video>() {
                @Override
                public void onResponse(Call<Video> call, retrofit2.Response<Video> response) {
                    if (response.isSuccessful()) {
                        Video video = response.body();
                        List<Video.ResultsDTO> officialTrailers = filterOfficialTrailers(video.getResults());

                        if (!officialTrailers.isEmpty()) {
                            // Obtener la clave del primer trailer oficial
                            String key = officialTrailers.get(0).getKey();

                            trailerButton.setOnClickListener(v -> {
                                Intent intent = new Intent(DetailActivity.this, TrailerActivity.class);
                                intent.putExtra("key", key);
                                startActivity(intent);
                            });
                        } else {
                           Toast.makeText(DetailActivity.this, "No official trailers found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        System.out.println("Error");
                    }
                }

                @Override
                public void onFailure(Call<Video> call, Throwable t) {
                    t.printStackTrace();
                }

                /**
                 * Filtra los videos para obtener solo el trailer oficial
                 * @param allVideos
                 * @return
                 */
                private List<Video.ResultsDTO> filterOfficialTrailers(List<Video.ResultsDTO> allVideos) {
                    List<Video.ResultsDTO> officialTrailers = new ArrayList<>();
                    for (Video.ResultsDTO video : allVideos) {
                        if ("Trailer".equalsIgnoreCase(video.getType()) && video.isOfficial()) {
                            officialTrailers.add(video);
                        }
                    }
                    return officialTrailers;
                }
            });
        }

    /**
     * Este método envía una solicitud a la API de TMDB para obtener los detalles de una serie de televisión.
     */
    private void sendTvRequest() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            TmdbApi tmdbApi = retrofit.create(TmdbApi.class);
            Call<Series> call4 = tmdbApi.getSerieDetails(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call4.enqueue(new Callback<Series>() {
                @Override
                public void onResponse(Call<Series> call, retrofit2.Response<Series> response) {
                    if (response.isSuccessful()) {
                        Series series = response.body();
                        handleSerieData(series);
                        addSerie(series);
                    } else {
                        String errorMessage = "Error: " + response.code() + " " + response.message();
                        if (response.errorBody() != null) {
                            errorMessage += " " + response.errorBody();
                        }
                        System.out.println(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<Series> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            Call<Credit> call5 = tmdbApi.getSerieCredits(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call5.enqueue(new Callback<Credit>() {
                @Override
                public void onResponse(Call<Credit> call, retrofit2.Response<Credit> response) {
                    if (response.isSuccessful()) {
                        Credit credit = response.body();
                        adapterActorList = new ActorAdapter(credit);
                        recyclerViewActor.setAdapter(adapterActorList);
                    } else {
                        String errorMessage = "Error: " + response.code() + " " + response.message();
                        if (response.errorBody() != null) {
                            errorMessage += " " + response.errorBody();
                        }
                        System.out.println(errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<Credit> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            Call<Video> call6 = tmdbApi.getSerieVideos(id, "176c7dcafd1c399aa33ca01eb0d50c19");
            call6.enqueue(new Callback<Video>() {
                @Override
                public void onResponse(Call<Video> call, retrofit2.Response<Video> response) {
                    if (response.isSuccessful()) {
                        Video video = response.body();
                        List<Video.ResultsDTO> officialTrailers = filterOfficialTrailers(video.getResults());

                        if (!officialTrailers.isEmpty()) {
                            String key = officialTrailers.get(0).getKey();

                            trailerButton.setOnClickListener(v -> {
                                Intent intent = new Intent(DetailActivity.this, TrailerActivity.class);
                                intent.putExtra("key", key);
                                startActivity(intent);
                            });
                        } else {
                            // No se encontraron trailers oficiales
                            System.out.println("No se encontraron trailers oficiales.");
                        }
                    } else {
                        String errorMessage = "Error: " + response.code() + " " + response.message();
                        if (response.errorBody() != null) {
                            errorMessage += " " + response.errorBody();
                        }
                        System.out.println(errorMessage);
                    }
                }
                @Override
                public void onFailure(Call<Video> call, Throwable t) {
                    t.printStackTrace();
                }

                /**
                 * Filtra los videos para obtener solo el trailer oficial
                 * @param allVideos
                 * @return officialTrailers
                 */
                private List<Video.ResultsDTO> filterOfficialTrailers(List<Video.ResultsDTO> allVideos) {
                    List<Video.ResultsDTO> officialTrailers = new ArrayList<>();

                    for (Video.ResultsDTO video : allVideos) {
                        if ("Trailer".equalsIgnoreCase(video.getType()) && video.isOfficial()) {
                            officialTrailers.add(video);
                        }
                    }
                    return officialTrailers;
                }
            });
        }

    /**
     * Este método maneja los datos de la película y los muestra en la interfaz de usuario.
     * @param movie
     */
    public void handleMovieData(Movie.ResultsDTO movie) {
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movie.getBackdropPath())
                .into(imageBackDrop);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(imageMovie);
        textTitle.setText(movie.getTitle());
        textDescription.setText(movie.getOverview());
    }

    /**
     * Este método maneja los datos de la serie de televisión y los muestra en la interfaz de usuario.
     * @param series
     */
    private void handleSerieData(Series series) {
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + series.getBackdropPath())
                .into(imageBackDrop);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + series.getPosterPath())
                .into(imageMovie);
        textTitle.setText(series.getName());
        textDescription.setText(series.getOverview());
    }

    /**
     * Este método maneja la lógica de añadir una película a la lista de favoritas.
     * @param movie
     */
    public void addMovie(Movie.ResultsDTO movie) {
        favButton.setOnClickListener(v -> {
            // Obtener el usuario actual
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                // Obtener una referencia a la película en la base de datos
                DatabaseReference movieRef = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(userId)
                        .child("favoriteMovies")
                        .child(String.valueOf(id));

                movieRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            movieRef.removeValue()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DetailActivity.this, "Movie removed from favorite list", Toast.LENGTH_SHORT).show();
                                        favButton.setColorFilter(Color.GRAY);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(DetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Map<String, Object> favoriteMovie = new HashMap<>();
                            favoriteMovie.put("id", id);
                            favoriteMovie.put("title", textTitle.getText().toString());
                            favoriteMovie.put("posterPath", "https://image.tmdb.org/t/p/w500" + movie.getPosterPath());

                            movieRef.setValue(favoriteMovie)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DetailActivity.this, "Movie added to favorite list", Toast.LENGTH_SHORT).show();
                                        favButton.setColorFilter(Color.MAGENTA);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(DetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DetailActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(DetailActivity.this, "You need to be logged in to add a movie to favorites", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Este método maneja la lógica de añadir una serie de televisión a la lista de favoritas.
     * @param series
     */
    private void addSerie(Series series){
        favButton.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                DatabaseReference serieRef = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(userId)
                        .child("favoriteSerie")
                        .child(String.valueOf(id));

                serieRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            serieRef.removeValue()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DetailActivity.this, "Show removed from favorite list", Toast.LENGTH_SHORT).show();
                                        favButton.setColorFilter(Color.GRAY);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(DetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Map<String, Object> favoriteSerie = new HashMap<>();
                            favoriteSerie.put("id", id);
                            favoriteSerie.put("name", textTitle.getText().toString());
                            favoriteSerie.put("posterPath", "https://image.tmdb.org/t/p/w500" + series.getPosterPath());

                            serieRef.setValue(favoriteSerie)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DetailActivity.this, "Show added to favorite list", Toast.LENGTH_SHORT).show();
                                        favButton.setColorFilter(Color.MAGENTA);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(DetailActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DetailActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(DetailActivity.this, "You need to be logged in to add a show to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Este método inicializa las vistas de la interfaz de usuario.
     */
    private void initView() {
        ratingNumber=findViewById(R.id.ratingNumber);
        ratingBar=findViewById(R.id.ratingBar);
        textTitle = findViewById(R.id.detail_movie_title);
        textDescription = findViewById(R.id.detail_movie_desc);
        imageMovie = findViewById(R.id.detail_movie_img);
        imageBackDrop = findViewById(R.id.detail_movie_cover);
        recyclerViewActor = findViewById(R.id.actorView);
        recyclerViewActor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        trailerButton=findViewById(R.id.trailerButton);
        favButton=findViewById(R.id.favButton);
    }
}
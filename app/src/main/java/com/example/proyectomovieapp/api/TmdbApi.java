package com.example.proyectomovieapp.api;



import com.example.proyectomovieapp.models.Credit;
import com.example.proyectomovieapp.models.Movie;
import com.example.proyectomovieapp.models.Series;
import com.example.proyectomovieapp.models.SerieResponse;
import com.example.proyectomovieapp.models.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {
    //Llama a la API de TMDB para obtener las peliculas que van a estrenarse
    @GET("movie/upcoming")
    Call<Movie> getUpcomingMovies(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener las mejores peliculas
    @GET("movie/top_rated")
    Call<Movie> getTopRatedMovies(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener las peliculas populares
    @GET("movie/popular")
    Call<Movie> getPopularMovies(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los detalles de una pelicula
    @GET("movie/{movie_id}")
    Call<Movie.ResultsDTO> getMovieDetails(@Path("movie_id") int movieId,@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los creditos de una pelicula(para sacar los actores)
    @GET("movie/{movie_id}/credits")
    Call<Credit> getMovieCredits(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los videos de una pelicula(para sacar el trailer)
    @GET("movie/{movie_id}/videos")
    Call<Video> getMovieVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener las series populares
    @GET("tv/popular")
    Call<SerieResponse> getPopularSeries(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener las mejores series
    @GET("tv/top_rated")
    Call<SerieResponse> getTopRatedSeries(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener las series que estan ahora en el aire
    @GET("tv/on_the_air")
    Call<SerieResponse> getOnTheAirSeries(@Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los detalles de una serie
    @GET("tv/{series_id}")
    Call<Series> getSerieDetails(@Path("series_id") int tvId, @Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los creditos de una serie(para sacar los actores)
    @GET("tv/{series_id}/credits")
    Call<Credit> getSerieCredits(@Path("series_id") int tvId, @Query("api_key") String apiKey);

    //Llama a la API de TMDB para obtener los videos de una serie(para sacar el trailer)
    @GET("tv/{series_id}/videos")
    Call<Video> getSerieVideos(@Path("series_id") int tvId, @Query("api_key") String apiKey);
}

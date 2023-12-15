package com.example.proyectomovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@lombok.NoArgsConstructor
@lombok.Data
public class Movie {


    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<ResultsDTO> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public List<ResultsDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultsDTO> results) {
        this.results = results;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class ResultsDTO {
        @SerializedName("adult")
        private boolean adult;
        @SerializedName("backdrop_path")
        private String backdropPath;
        @SerializedName("genre_ids")
        private List<Integer> genreIds;
        @SerializedName("id")
        private int id;
        @SerializedName("original_language")
        private String originalLanguage;
        @SerializedName("original_title")
        private String originalTitle;
        @SerializedName("overview")
        private String overview;
        @SerializedName("popularity")
        private double popularity;
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("release_date")
        private String releaseDate;
        @SerializedName("title")
        private String title;
        @SerializedName("video")
        private boolean video;
        @SerializedName("vote_average")
        private double voteAverage;
        @SerializedName("vote_count")
        private int voteCount;

        public boolean isAdult() {
            return adult;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public int getId() {
            return id;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getOverview() {
            return overview;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public boolean isVideo() {
            return video;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }
    }
}

package com.example.proyectomovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Series {

    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("created_by")
    private List<CreatedByDTO> createdBy;
    @SerializedName("episode_run_time")
    private List<Integer> episodeRunTime;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("genres")
    private List<LastEpisodeToAirDTO> genres;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private int id;
    @SerializedName("in_production")
    private boolean inProduction;
    @SerializedName("languages")
    private List<String> languages;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("last_episode_to_air")
    private LastEpisodeToAirDTO lastEpisodeToAir;
    @SerializedName("name")
    private String name;
    @SerializedName("next_episode_to_air")
    private Object nextEpisodeToAir;
    @SerializedName("networks")
    private List<NetworksDTO> networks;
    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;
    @SerializedName("origin_country")
    private List<String> originCountry;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("production_companies")
    private List<NetworksDTO> productionCompanies;
    @SerializedName("production_countries")
    private List<ProductionCountriesDTO> productionCountries;
    @SerializedName("seasons")
    private List<SeasonsDTO> seasons;
    @SerializedName("spoken_languages")
    private List<SpokenLanguagesDTO> spokenLanguages;
    @SerializedName("status")
    private String status;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("type")
    private String type;
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

    public List<CreatedByDTO> getCreatedBy() {
        return createdBy;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public List<LastEpisodeToAirDTO> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public LastEpisodeToAirDTO getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public Object getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public List<NetworksDTO> getNetworks() {
        return networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
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

    public List<NetworksDTO> getProductionCompanies() {
        return productionCompanies;
    }

    public List<ProductionCountriesDTO> getProductionCountries() {
        return productionCountries;
    }

    public List<SeasonsDTO> getSeasons() {
        return seasons;
    }

    public List<SpokenLanguagesDTO> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getType() {
        return type;
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

    public void setCreatedBy(List<CreatedByDTO> createdBy) {
        this.createdBy = createdBy;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setGenres(List<LastEpisodeToAirDTO> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public void setLastEpisodeToAir(LastEpisodeToAirDTO lastEpisodeToAir) {
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNextEpisodeToAir(Object nextEpisodeToAir) {
        this.nextEpisodeToAir = nextEpisodeToAir;
    }

    public void setNetworks(List<NetworksDTO> networks) {
        this.networks = networks;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
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

    public void setProductionCompanies(List<NetworksDTO> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public void setProductionCountries(List<ProductionCountriesDTO> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setSeasons(List<SeasonsDTO> seasons) {
        this.seasons = seasons;
    }

    public void setSpokenLanguages(List<SpokenLanguagesDTO> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @NoArgsConstructor
    @Data
    public static class LastEpisodeToAirDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overview;
        @SerializedName("vote_average")
        private double voteAverage;
        @SerializedName("vote_count")
        private int voteCount;
        @SerializedName("air_date")
        private String airDate;
        @SerializedName("episode_number")
        private int episodeNumber;
        @SerializedName("production_code")
        private String productionCode;
        @SerializedName("runtime")
        private int runtime;
        @SerializedName("season_number")
        private int seasonNumber;
        @SerializedName("show_id")
        private int showId;
        @SerializedName("still_path")
        private String stillPath;
    }

    @NoArgsConstructor
    @Data
    public static class CreatedByDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("credit_id")
        private String creditId;
        @SerializedName("name")
        private String name;
        @SerializedName("gender")
        private int gender;
        @SerializedName("profile_path")
        private String profilePath;
    }

    @NoArgsConstructor
    @Data
    public static class NetworksDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("logo_path")
        private String logoPath;
        @SerializedName("name")
        private String name;
        @SerializedName("origin_country")
        private String originCountry;
    }

    @NoArgsConstructor
    @Data
    public static class ProductionCountriesDTO {
        @SerializedName("iso_3166_1")
        private String iso31661;
        @SerializedName("name")
        private String name;
    }

    @NoArgsConstructor
    @Data
    public static class SeasonsDTO {
        @SerializedName("air_date")
        private String airDate;
        @SerializedName("episode_count")
        private int episodeCount;
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overview;
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("season_number")
        private int seasonNumber;
        @SerializedName("vote_average")
        private double voteAverage;

        public String getAirDate() {
            return airDate;
        }

        public int getEpisodeCount() {
            return episodeCount;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOverview() {
            return overview;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public int getSeasonNumber() {
            return seasonNumber;
        }

        public double getVoteAverage() {
            return voteAverage;
        }
    }

    @NoArgsConstructor
    @Data
    public static class SpokenLanguagesDTO {
        @SerializedName("english_name")
        private String englishName;
        @SerializedName("iso_639_1")
        private String iso6391;
        @SerializedName("name")
        private String name;
    }
}


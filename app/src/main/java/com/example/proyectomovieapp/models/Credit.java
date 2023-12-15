package com.example.proyectomovieapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Credit {


    @SerializedName("id")
    private int id;
    @SerializedName("cast")
    private List<CastDTO> cast;
    @SerializedName("crew")
    private List<CrewDTO> crew;

    public int getId() {
        return id;
    }

    public List<CastDTO> getCast() {
        return cast;
    }

    public List<CrewDTO> getCrew() {
        return crew;
    }

    @NoArgsConstructor
    @Data
    public static class CastDTO {
        @SerializedName("adult")
        private boolean adult;
        @SerializedName("gender")
        private int gender;
        @SerializedName("id")
        private int id;
        @SerializedName("known_for_department")
        private String knownForDepartment;
        @SerializedName("name")
        private String name;
        @SerializedName("original_name")
        private String originalName;
        @SerializedName("popularity")
        private double popularity;
        @SerializedName("profile_path")
        private String profilePath;
        @SerializedName("cast_id")
        private int castId;
        @SerializedName("character")
        private String character;
        @SerializedName("credit_id")
        private String creditId;
        @SerializedName("order")
        private int order;

        public boolean isAdult() {
            return adult;
        }

        public int getGender() {
            return gender;
        }

        public int getId() {
            return id;
        }

        public String getKnownForDepartment() {
            return knownForDepartment;
        }

        public String getName() {
            return name;
        }

        public String getOriginalName() {
            return originalName;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public int getCastId() {
            return castId;
        }

        public String getCharacter() {
            return character;
        }

        public String getCreditId() {
            return creditId;
        }

        public int getOrder() {
            return order;
        }
    }

    @NoArgsConstructor
    @Data
    public static class CrewDTO {
        @SerializedName("adult")
        private boolean adult;
        @SerializedName("gender")
        private int gender;
        @SerializedName("id")
        private int id;
        @SerializedName("known_for_department")
        private String knownForDepartment;
        @SerializedName("name")
        private String name;
        @SerializedName("original_name")
        private String originalName;
        @SerializedName("popularity")
        private double popularity;
        @SerializedName("profile_path")
        private String profilePath;
        @SerializedName("credit_id")
        private String creditId;
        @SerializedName("department")
        private String department;
        @SerializedName("job")
        private String job;

        public boolean isAdult() {
            return adult;
        }

        public int getGender() {
            return gender;
        }

        public int getId() {
            return id;
        }

        public String getKnownForDepartment() {
            return knownForDepartment;
        }

        public String getName() {
            return name;
        }

        public String getOriginalName() {
            return originalName;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public String getCreditId() {
            return creditId;
        }

        public String getDepartment() {
            return department;
        }

        public String getJob() {
            return job;
        }
    }
}

package com.connection.assessment.model.dto;

import com.connection.assessment.model.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieDTO {
    private Long id;
    private int movieRank;
    private String title;
    private String description;
    private int releaseYear;
    private int runtime;
    private double rating;
    private int votes;
    private double revenue;
    private int metaScore;
    private List<String> actors;
    private String director;

    MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.movieRank = movie.getMovieRank();
        this.title = movie.getTitle();
    }
}

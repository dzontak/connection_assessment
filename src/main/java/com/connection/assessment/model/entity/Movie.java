package com.connection.assessment.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Movie {

    @ManyToMany()
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    List<Genre> genres;
    @ManyToMany()
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    List<Actor> actors;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer movieRank;
    private String title;
    private String description;
    private Integer releaseYear;
    private Integer runtime;
    private Double rating;
    private Integer votes;
    private Double revenue;
    private Integer metaScore;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;
}

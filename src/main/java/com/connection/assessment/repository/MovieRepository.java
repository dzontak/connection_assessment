package com.connection.assessment.repository;

import com.connection.assessment.model.entity.Genre;
import com.connection.assessment.model.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitleAndReleaseYear(String title, int year);

    Optional<List<Movie>> findByGenres(Genre genre);
}


package com.connection.assessment.repository;

import com.connection.assessment.model.entity.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByCode(String code);
}

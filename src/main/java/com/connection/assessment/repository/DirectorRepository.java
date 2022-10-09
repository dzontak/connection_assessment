package com.connection.assessment.repository;

import com.connection.assessment.model.entity.Director;
import org.springframework.data.repository.CrudRepository;

public interface DirectorRepository extends CrudRepository<Director, Long> {
    Director findByName(String name);
}

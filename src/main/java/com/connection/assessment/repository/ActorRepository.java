package com.connection.assessment.repository;

import com.connection.assessment.model.entity.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
    Actor findByName(String name);
}

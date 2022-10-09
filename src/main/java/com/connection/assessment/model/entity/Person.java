package com.connection.assessment.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
}

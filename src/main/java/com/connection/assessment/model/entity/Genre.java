package com.connection.assessment.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String code;
}

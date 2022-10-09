DROP TABLE IF EXISTS director;
CREATE TABLE director
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS movie;
CREATE TABLE movie
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    movie_rank   SMALLINT,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    release_year INT          NOT NULL,
    runtime      INT,
    rating       DECIMAL,
    votes        INT,
    revenue      DECIMAL,
    meta_score   SMALLINT,
    director_id  BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (director_id) REFERENCES director (id)
    );


DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS movie_genre;
CREATE TABLE movie_genre
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    movie_Id BIGINT NOT NULL,
    genre_Id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (genre_Id) REFERENCES genre (id),
    FOREIGN KEY (movie_Id) REFERENCES movie (id)
);


DROP TABLE IF EXISTS actor;
CREATE TABLE actor
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS movie_actor;
CREATE TABLE movie_actor
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    actor_id BIGINT NOT NULL,
    movie_Id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (actor_id) REFERENCES Actor (id),
    FOREIGN KEY (movie_Id) REFERENCES Movie (id)
);

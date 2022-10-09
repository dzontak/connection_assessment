--SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE IF NOT EXISTS director
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movie
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

CREATE TABLE IF NOT EXISTS genre
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movie_genre
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    movie_Id BIGINT NOT NULL,
    genre_Id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (genre_Id) REFERENCES genre (id),
    FOREIGN KEY (movie_Id) REFERENCES movie (id)
);

CREATE TABLE IF NOT EXISTS actor
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS movie_actor
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    actor_id BIGINT NOT NULL,
    movie_Id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (actor_id) REFERENCES Actor (id),
    FOREIGN KEY (movie_Id) REFERENCES Movie (id)
);

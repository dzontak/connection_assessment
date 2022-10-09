package com.connection.assessment.service;

import com.connection.assessment.model.entity.Actor;
import com.connection.assessment.model.entity.Director;
import com.connection.assessment.model.entity.Genre;
import com.connection.assessment.model.entity.Movie;
import com.connection.assessment.repository.ActorRepository;
import com.connection.assessment.repository.DirectorRepository;
import com.connection.assessment.repository.GenreRepository;
import com.connection.assessment.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    GenreRepository genreRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    MovieRepository movieRepository;

    public Movie createMovie(Movie movie) {
        logger.info("Post movie: " + movie);
        if (!CollectionUtils.isEmpty(movie.getGenres())) {
            for (int i = 0; i < movie.getGenres().size(); i++) {
                String code = movie.getGenres().get(i).getCode();
                Genre genre = genreRepository.findByCode(code);
                if (genre == null) {
                    genre = new Genre();
                    genre.setCode(code);
                    genre = genreRepository.save(genre);
                }
                movie.getGenres().set(i, genre);
            }
        }

        if (!CollectionUtils.isEmpty(movie.getActors())) {
            for (int i = 0; i < movie.getActors().size(); i++) {
                String name = movie.getActors().get(i).getName();
                Actor actor = actorRepository.findByName(name);
                if (actor == null) {
                    actor = new Actor();
                    actor.setName(name);
                    actor = actorRepository.save(actor);
                }
                movie.getActors().set(i, actor);
            }
        }

        if (movie.getDirector() != null) {
            String directorName = movie.getDirector().getName();
            Director director = directorRepository.findByName(directorName);
            if (director == null) {
                director = new Director();
                director.setName(directorName);
                director = directorRepository.save(director);
            }
            movie.setDirector(director);
        }

        return movieRepository.save(movie);
    }

    /**
     * Update a movie
     *
     * @param newMovie A movie with updated attributes
     * @param id       A unique identifer of the movie to update
     * @return An updated movie
     */
    public Movie updateMovie(Movie newMovie, Long id) {
        Movie movie = this.getMovie(id);
        logger.info("Patch movie with id " + id + " " + newMovie);

        // overwrite genres
        if (!CollectionUtils.isEmpty(newMovie.getGenres())) {
            List<Genre> newGenresList = new ArrayList<>();
            for (Genre newGenres : newMovie.getGenres()) {
                Genre genre = genreRepository.findByCode(newGenres.getCode());
                if (genre == null) {
                    genre = new Genre();
                    genre.setCode(newGenres.getCode());
                    genre = genreRepository.save(genre);
                }
                newGenresList.add(genre);
            }
            movie.setGenres(newGenresList);
        }

        // Overwrite actors
        if (!CollectionUtils.isEmpty(newMovie.getActors())) {
            List<Actor> newActorList = new ArrayList<>();
            for (Actor newActor : newMovie.getActors()) {
                Actor actor = actorRepository.findByName(newActor.getName());
                if (actor == null) {
                    actor = new Actor();
                    actor.setName(newActor.getName());
                    actor = actorRepository.save(actor);
                }
                newActorList.add(actor);
            }
            movie.setActors(newActorList);
        }

        // overwrite Director
        if (newMovie.getDirector() != null) {
            String newDirectorName = newMovie.getDirector().getName();
            Director director = directorRepository.findByName(newDirectorName);
            if (director == null) {
                director = new Director();
                director.setName(newDirectorName);
                director = directorRepository.save(director);
            }
            movie.setDirector(director);
        }

        if (newMovie.getMovieRank() != null) movie.setMovieRank(newMovie.getMovieRank());
        if (newMovie.getTitle() != null) movie.setTitle(newMovie.getTitle());
        if (newMovie.getDescription() != null) movie.setDescription(newMovie.getDescription());
        if (newMovie.getReleaseYear() != null) movie.setReleaseYear(newMovie.getReleaseYear());
        if (newMovie.getRuntime() != null) movie.setRuntime(newMovie.getRuntime());
        if (newMovie.getRating() != null) movie.setRating(newMovie.getRating());
        if (newMovie.getVotes() != null) movie.setVotes(newMovie.getVotes());
        if (newMovie.getRevenue() != null) movie.setRevenue(newMovie.getRevenue());
        if (movie.getMetaScore() != null) movie.setMetaScore(newMovie.getMetaScore());

        return movieRepository.save(movie);
    }

    /**
     * Gets a movie by id
     *
     * @param id A unique identifier
     * @return A movie
     * @throws ResponseStatusException if movie is not found for the id
     */
    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found for id: " + id));
    }

    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMovieForGenre(String genre) {
        Genre genreEntity = genreRepository.findByCode(genre);
        if (genreEntity == null) return new ArrayList<>();

        return movieRepository.findByGenres(genreEntity).orElse(new ArrayList<>());
    }

    /**
     * Delete a movie
     *
     * @param id a unique identifier
     */
    public void deleteMovie(Long id) {
        // throw 404 if movie for id does not exist.
        if (this.getMovie(id) != null) {
            movieRepository.deleteById(id);
        }
    }
}

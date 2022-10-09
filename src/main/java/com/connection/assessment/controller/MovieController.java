package com.connection.assessment.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
//TODO: Introduce Service Layer
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    MovieController(MovieRepository repository, GenreRepository genreRepository,
                    ActorRepository actorRepository, DirectorRepository directorRepository) {
        this.movieRepository = repository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
    }


    /**
     * Request:
     * URL: /movies
     * Method: GET
     * Response:
     * Returns a collection of all movies. Example: [{"id":1,R"rank:1,"title":"Guardians of the Galaxy","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76},{"id":2,"rank":2,"title":"Prometheus","genre":["Mystery","Adventure","Sci-Fi"],"description":"Following clues to the origin of mankind, a team finds a structure on a distant moon, but they soon realize they are not alone.","director":"Ridley Scott","actors":["Noomi Rapace","Logan Marshall-Green","Michael Fassbender","Charlize Theron"],"year":2012,"runtime": 124,"rating":7,"votes": 485820,"revenue":126.46,"metascore":65},........]
     * The response code is 200, and the response body is a list of movies.
     *
     * @return all Movies
     */
    @GetMapping("/movies")
    List<Movie> all() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Request:
     * URL: /movies/{id}
     * Method: GET
     * Response:
     * Returns a movie object equal to the id supplied. Example: {"id":1,"rank" :1,"title":"Guardians of the Galaxy","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76}
     * The response code is 200, and the response body is a movie object equal to the id provided.
     * In case there are no such a movie return status code 404.
     *
     * @param id A movie id
     * @return A movie or 404 if now found
     */
    @GetMapping("/movies/{id}")
    Movie one(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found for id: " + id
                ));
    }

    /**
     * Request:
     * URL: /movies/filter/{genre}
     * Method: GET
     * Response:
     * Returns a collection of all movies equal to the genre supplied. Example: [{"id":1,R"rank:1,"title":"Guardians of the Galaxy","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76},{"id":2,"rank":2,"title":"Prometheus","genre":["Mystery","Adventure","Sci-Fi"],"description":"Following clues to the origin of mankind, a team finds a structure on a distant moon, but they soon realize they are not alone.","director":"Ridley Scott","actors":["Noomi Rapace","Logan Marshall-Green","Michael Fassbender","Charlize Theron"],"year":2012,"runtime": 124,"rating":7,"votes": 485820,"revenue":126.46,"metascore":65},........]
     * The response code is 200, and the response body is a list of movies equal with the genre provided.
     *
     * @param genre
     * @return Returns a collection of all movies equal to the {@code genre} supplied.
     */
    @GetMapping("/movies/filter/{genre}")
    List<Movie> filterMovieByGenre(@PathVariable String genre) {

        Genre genreEntity = genreRepository.findByCode(genre);
        if (genreEntity == null) return new ArrayList<>();

        return movieRepository.findByGenres(genreEntity)
                .orElse(new ArrayList<>());
    }

    /**
     * Request:
     * URL: /movies
     * Method: POST
     * Payload (Example): {"rank" :1,"title":"Guardians of the Galaxy","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76}
     * Response:
     * The request contains an object with all the data about the movie.
     * The response code is 201 and the response body is the movie object created.
     * Example: {"id":1,"rank" :1,"title":"Guardians of the Galaxy","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76}
     *
     * @param movie A movie to create
     * @return A new movie that was created.
     */
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    Movie newMovie(@RequestBody Movie movie) {

        logger.info("Post movie: " + movie);
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


        String directorName = movie.getDirector().getName();
        Director director = directorRepository.findByName(directorName);
        if (director == null) {
            director = new Director();
            director.setName(directorName);
            director = directorRepository.save(director);
        }
        movie.setDirector(director);

        return movieRepository.save(movie);
    }


    /**
     * Request:
     * URL: /movies/{id}
     * Method: PATCH
     * Payload (Example): {"rank" :1,"title":"Guardians of the Galaxy - Part 1","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76}
     * Response:
     * Returns the movie object patched. Example: {"id":1,"rank" :1,"title":"Guardians of the Galaxy - Part 1","genre":["Action","Adventure","Sci-Fi"],"description":"A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.","director":"James Gunn","actors":["Chris Pratt","Vin Diesel","Bradley Cooper","Zoe Saldana"],"year":2014,"runtime":121,"rating":8.1,"votes":757074,"revenue":333.13,"metascore":76}
     * The response code is 200 and the response body is the movie object patched.
     * In case there are no such movies return status code 404.
     *
     * @param newMovie A movie to update
     * @param id       a unique identifier for the movie
     * @return An updated movie
     */
    @PatchMapping("/movies/{id}")
    Movie updateMovie(@RequestBody Movie newMovie, @PathVariable Long id) {

        Movie movie = this.one(id);
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
     * Request:
     * URL: /movies/{id}:
     * Method: DELETE
     * Response:
     * The response code is 200.
     * In case there are no such movies return status code 404.
     *
     * @param id A unique identifier for the movie
     */
    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable Long id) {
        // throw 404 if movie for id does not exist.
        if (this.one(id) != null) {
            movieRepository.deleteById(id);
        }
    }
}

package com.connection.assessment.controller;

import com.connection.assessment.model.entity.Movie;
import com.connection.assessment.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// TODO: Introduce Service Layer and ModelMapper
// https://www.javaguides.net/2021/02/spring-boot-dto-example-entity-to-dto.html
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieService movieService;

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
    Iterable<Movie> getMovies() {
        return movieService.getMovies();
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
        return movieService.getMovie(id);
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
    List<Movie> getMovieForGenre(@PathVariable String genre) {
        return movieService.getMovieForGenre(genre);

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
     * @return A new movie that was created
     */
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    Movie createMovie(@RequestBody Movie movie) {
        return movieService.createMovie(movie);
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
        return this.movieService.updateMovie(newMovie, id);
    }

    /**
     * Request:
     * URL: /movies/{id}
     * Method: DELETE
     * Response:
     * The response code is 200.
     * In case there are no such movies return status code 404.
     *
     * @param id A unique identifier for the movie
     */
    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable Long id) {
        this.movieService.deleteMovie(id);
    }
}

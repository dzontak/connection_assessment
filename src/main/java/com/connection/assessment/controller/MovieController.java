package com.connection.assessment.controller;

import com.connection.assessment.model.entity.Movie;
import com.connection.assessment.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found movies", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))})})
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
    @Operation(summary = "Get a movie by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found a Movie", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}), @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)})
    @GetMapping("/movies/{id}")
    Movie one(@Parameter(description = "id of movie to be searched") @PathVariable Long id) {
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
    @Operation(summary = "Get movies for a genre")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found movies in genre", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))})})
    List<Movie> getMovieForGenre(@Parameter(description = "genre to be searched (Action, Drama, Adventure)") @PathVariable String genre) {
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

    @Operation(summary = "Create a new Movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Movie created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}), @ApiResponse(responseCode = "400", description = "Invalid Movie supplied", content = @Content)})
    @PostMapping("/movies")
    @ResponseStatus(HttpStatus.CREATED)
    Movie createMovie(@Parameter(description = "Movie to be created") @RequestBody Movie movie) {
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

    @Operation(summary = "Update a movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Movie updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}), @ApiResponse(responseCode = "400", description = "Invalid Movie supplied", content = @Content), @ApiResponse(responseCode = "404", description = "No movie found with id", content = @Content)})
    @PatchMapping("/movies/{id}")
    Movie updateMovie(@Parameter(description = "Movie with new attributes to be updated") @RequestBody Movie newMovie, @Parameter(description = "id of movie to be updated") @PathVariable Long id) {
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
    @Operation(summary = "Delete a movie")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Movie deleted", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))}), @ApiResponse(responseCode = "404", description = "No movie found with id", content = @Content)})
    @DeleteMapping("/movies/{id}")
    void deleteMovie(@Parameter(description = "id of movie to be deleted") @PathVariable Long id) {
        this.movieService.deleteMovie(id);
    }
}

package com.connection.assessment;


import com.connection.assessment.model.entity.Actor;
import com.connection.assessment.model.entity.Director;
import com.connection.assessment.model.entity.Genre;
import com.connection.assessment.model.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieControllerTest extends AbstractTest {
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldGetMovies() throws Exception {

        String uri = "/movies";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie[] movieList = super.mapFromJson(content, Movie[].class);
        assertTrue(movieList.length > 0);
    }


    @Test
    public void shouldGetOneMovie() throws Exception {

        String uri = "/movies/9";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie response = super.mapFromJson(content, Movie.class);
        assertEquals("Guardians of the Galaxy", response.getTitle());
    }

    @Test
    public void shouldFindAllMoviesForGenre() throws Exception {

        String uri = "/movies/filter/Action";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie[] movieList = super.mapFromJson(content, Movie[].class);
        assertTrue(movieList.length > 0);
    }


    @Test
    public void shouldFindNoMoviesForGenreThatDoesNotExist() throws Exception {

        String uri = "/movies/filter/DOESNOTEXIST";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie[] movieList = super.mapFromJson(content, Movie[].class);
        assertTrue(movieList.length == 0);
    }


    @Test
    public void shouldUpdateOneMovie() throws Exception {
        String uri = "/movies/9";
        Movie movie = new Movie();
        movie.setTitle("Test Title");
        movie.setReleaseYear(2022);
        movie.setMovieRank(1);
        movie.setDescription("Test Description");
        movie.setRuntime(111);
        movie.setRating(9.1);
        movie.setRevenue(1.1);
        movie.setMetaScore(77);

        Genre actionGenre = new Genre();
        actionGenre.setCode("Action");
        List<Genre> genres = new ArrayList<>();
        genres.add(actionGenre);
        movie.setGenres(genres);

        Director director = new Director();
        director.setName("Test Director");

        movie.setDirector(director);


        Actor actor = new Actor();
        actor.setName("Test Actor");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        movie.setActors(actors);


        String inputJson = super.mapToJson(movie);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie response = super.mapFromJson(content, Movie.class);
        assertEquals(movie.getTitle(), response.getTitle());
        assertEquals(movie.getReleaseYear(), response.getReleaseYear());
        assertEquals(movie.getMovieRank(), response.getMovieRank());
        assertEquals(movie.getDescription(), response.getDescription());
        assertEquals(movie.getRuntime(), response.getRuntime());
        assertEquals(movie.getRating(), response.getRating());
        assertEquals(movie.getRevenue(), response.getRevenue());
        assertEquals(movie.getMetaScore(), response.getMetaScore());
        assertEquals(movie.getGenres().get(0).getCode(), response.getGenres().get(0).getCode());
        assertEquals(movie.getDirector().getName(), response.getDirector().getName());
        assertEquals(movie.getActors().get(0).getName(), response.getActors().get(0).getName());

    }


    @Test
    public void shouldGet404IfMovieIdIsNotFoundById() throws Exception {
        String uri = "/movies/99999999";
        Movie movie = new Movie();
        movie.setTitle("Test Title");
        movie.setReleaseYear(2022);

        String inputJson = super.mapToJson(movie);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }


    @Test
    public void shouldDeleteOneMovie() throws Exception {
        String uri = "/movies/16";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void shouldGet404WhenDeletingMovieForIdThatDoesNotExist() throws Exception {
        String uri = "/movies/999999";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }


    @Test
    public void shouldCreateOneMovie() throws Exception {
        String uri = "/movies";
        Movie movie = new Movie();
        movie.setTitle("Test Title");
        movie.setReleaseYear(2022);
        movie.setDescription("Test Description");

        String inputJson = super.mapToJson(movie);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie response = super.mapFromJson(content, Movie.class);
        assertEquals(movie.getTitle(), response.getTitle());
        assertEquals(movie.getReleaseYear(), response.getReleaseYear());
    }

}
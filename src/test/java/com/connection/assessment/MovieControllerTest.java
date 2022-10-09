package com.connection.assessment;


import com.connection.assessment.model.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieControllerTest extends AbstractTest {
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldGetAllMovies() throws Exception {

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

        String inputJson = super.mapToJson(movie);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movie response = super.mapFromJson(content, Movie.class);
        assertEquals(movie.getTitle(), response.getTitle());
        assertEquals(movie.getReleaseYear(), response.getReleaseYear());
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
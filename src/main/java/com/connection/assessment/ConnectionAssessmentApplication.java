package com.connection.assessment;

import com.connection.assessment.model.entity.Actor;
import com.connection.assessment.model.entity.Director;
import com.connection.assessment.model.entity.Genre;
import com.connection.assessment.model.entity.Movie;
import com.connection.assessment.repository.ActorRepository;
import com.connection.assessment.repository.DirectorRepository;
import com.connection.assessment.repository.GenreRepository;
import com.connection.assessment.repository.MovieRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ConnectionAssessmentApplication {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionAssessmentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConnectionAssessmentApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner loadMovieData(
            MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository,
            DirectorRepository directorRepository
    ) {
        return args -> {

            File file = ResourceUtils.getFile("classpath:IMDB-Movie-Data.csv");
            Reader in = new FileReader(file.getAbsolutePath());
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("Rank", "Title", "Genre", "Description", "Director", "Actors", "Year", "Runtime (Minutes)", "Rating", "Votes", "Revenue (Millions)", "Metascore").parse(in);
            for (CSVRecord record : records) {

                Movie movie = new Movie();

                // skip the header row
                String rank = record.get("Rank");
                if (rank.equals("Rank")) continue;

                String[] genres = record.get("Genre").split(",");
                List<Genre> movieGenres = new ArrayList<>();
                for (String genreCode : genres) {
                    Genre genre = genreRepository.findByCode(genreCode);
                    if (genre == null) {
                        genre = new Genre();
                        genre.setCode(genreCode);
                        genre = genreRepository.save(genre);
                        logger.debug("Saved genre: " + genre);
                    }
                    movieGenres.add(genre);
                }

                movie.setGenres(movieGenres);

                String[] actors = record.get("Actors").split(",");
                List<Actor> movieActors = new ArrayList<>();
                for (String name : actors) {
                    Actor actor = actorRepository.findByName(name);
                    if (actor == null) {
                        actor = new Actor();
                        actor.setName(name);
                        actor = actorRepository.save(actor);
                        logger.debug("actor: " + actor);
                    }
                    movieActors.add(actor);
                }

                movie.setActors(movieActors);

                String directorName = record.get("Director");
                Director director = directorRepository.findByName(directorName);
                if (director == null) {
                    director = new Director();
                    director.setName(directorName);
                    director = directorRepository.save(director);
                    logger.debug("Saved director " + director);
                }
                movie.setDirector(director);

                movie.setMovieRank(Integer.parseInt(record.get("Rank")));
                movie.setTitle(record.get("Title"));


                //  Value too long for column "DESCRIPTION CHARACTER VARYING(255)": "'Twin Peaks before Twin Peaks (1990) and at the same time not always and entirel... (309)";
                // TODO: figure out how to represent strings over 255 in H2.
                String desc = record.get("Description");
                if (desc.length() > 255) {
                    desc = desc.substring(0, 255);
                }

                movie.setDescription(desc);
                movie.setReleaseYear(Integer.parseInt(record.get("Year")));
                movie.setRuntime(Integer.parseInt(record.get("Runtime (Minutes)")));
                movie.setRating(Double.parseDouble(record.get("Rating")));
                movie.setVotes(Integer.parseInt(record.get("Votes")));
                if (StringUtils.hasLength(record.get("Revenue (Millions)"))) {
                    movie.setRevenue(Double.parseDouble(record.get("Revenue (Millions)")));
                }
                if (StringUtils.hasLength(record.get("Metascore"))) {
                    movie.setMetaScore(Integer.parseInt(record.get("Metascore")));
                }

                movieRepository.save(movie);

                logger.debug("Saved Movie *********************: " + movie);
            }
        };
    }
}


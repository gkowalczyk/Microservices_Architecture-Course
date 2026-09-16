package com.example.movieservicemovierecommendationsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MovieServiceMovieRecommendationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieServiceMovieRecommendationSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner addMovies(MovieRepo repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Movie(
                                null,
                                "SCI_FI",
                                "Interstellar",
                                2014,
                                8.7
                        ),
                        new Movie(
                                null,
                                "SCI_FI",
                                "The Matrix",
                                1999,
                                8.7
                        ),
                        new Movie(
                                null,
                                "SCI_FI",
                                "Blade Runner 2049",
                                2017,
                                8.0
                        ),
                        new Movie(
                                null,
                                "SCI_FI",
                                "Arrival",
                                2016,
                                7.9
                        ),
                        new Movie(
                                null,
                                "CRIME",
                                "The Godfather",
                                1972,
                                9.2
                        )
                ));
            }
        };
    }
}

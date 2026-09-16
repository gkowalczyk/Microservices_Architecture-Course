package com.example.movieservicemovierecommendationsystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String genre;
    private String title;
    private  Integer releaseYear;
    private  Double rating;

    public Movie(String genre, String title, Integer releaseYear, Double rating) {
        this.genre = genre;
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public Movie(Long id, String genre, String title, Integer releaseYear, Double rating) {
        this.id = id;
        this.genre = genre;
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }
}

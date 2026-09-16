package com.example.movieservicemovierecommendationsystem;

public class MovieNotFoundException extends RuntimeException {


    public MovieNotFoundException(Long id) {
        super("Nie znaleziono filmu o ID:" + id);
    }
}

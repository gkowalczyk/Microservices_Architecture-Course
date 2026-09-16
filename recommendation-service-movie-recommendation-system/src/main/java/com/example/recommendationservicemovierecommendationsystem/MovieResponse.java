package com.example.recommendationservicemovierecommendationsystem;

public record MovieResponse(
        Long id,
        String title,
        String genre,
        Integer releaseYear,
        Double rating
) {
}

package com.example.movieservicemovierecommendationsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreIgnoreCaseAndRatingGreaterThanEqualOrderByRatingDesc(
            String genre,
            Double minimumRating);
}

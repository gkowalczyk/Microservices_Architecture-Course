package com.example.movieservicemovierecommendationsystem;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepo movieRepo;
    private final Tracer tracer;

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {

        Span span = tracer.currentSpan();
        if (span != null) {
            span.tag("movie.id", id.toString());
            span.tag("database.operation", "Select_by_id");
        }

        return movieRepo.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) {
        Span span = tracer.currentSpan();
        if (span != null) {
            span.tag("movie.genre", movie.toString());
            span.tag("database.operation", "Insert");
        }
        return movieRepo.save(movie);

    }

    @GetMapping
    public List<Movie> findMovies(@RequestParam String genre,
                                  @RequestParam Double minimumRating) {

        Span span = tracer.currentSpan();
        span.tag("movie.query.genre", genre);
        span.tag("movie.query.min.rating", minimumRating);

        List<Movie> movieList = movieRepo.findByGenreIgnoreCaseAndRatingGreaterThanEqualOrderByRatingDesc(
                genre, minimumRating

        );
        span.tag(
                "movie.result_count",
                String.valueOf(movieList.size()));
        span.tag(
                "database.operation",
                "SELECT");

        return movieList;
    }
}


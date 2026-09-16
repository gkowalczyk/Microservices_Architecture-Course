package com.example.recommendationservicemovierecommendationsystem;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final MovieClient movieClient;
    private final Tracer tracer;

    public List<MovieResponse> recommend(
            String genre,
            Double minimumRating,
            Integer limit
    ) {
        Span span = tracer.currentSpan();
        span.tag(
                "recommendation.minimum_rating",
                minimumRating.toString()
        );

        span.tag(
                "recommendation.limit",
                limit.toString()

                );

             span.event("recommendations-selected");

    return movieClient.findCandidates(
            genre,
            minimumRating
    )
            .stream()
            .sorted(Comparator.comparing(MovieResponse::rating)
                    .reversed())
            .limit(limit)
            .collect(Collectors.toList());


    }
}
